# call static
import sys
import os
import re
import time
from Command import init
from Command import static_analysis
from Command import generate_test
from Command import compile_test
from Command import save_info
from Command import remove_test_file
from CallGraph import choose_call_graph
from CallGraph import get_static_call_graph
from CallGraph import get_target_class_name
from CallGraph import load_class_path
from CleanSeed import extract_seed
from Instrumentor import run_test

# part0 init
# get basic information from sh command
cve_number = sys.argv[1]
parent_name = sys.argv[2]
child_name = sys.argv[3]
vul_function_name = sys.argv[4]
target_jar_name = sys.argv[5]
vulner_show = sys.argv[6]


vul_class_name = vul_function_name.split(":")[0]

# make project name
if parent_name != child_name:
    child_name = f'{parent_name}/{child_name}'
# do init process, like maven install
total_project_dir = f"/VESTA/resource/{cve_number}/{parent_name}"
target_project_dir = f"/VESTA/resource/{cve_number}/{child_name}"
init(total_project_dir, target_project_dir)

target_class_path = f"{target_project_dir}/target/test-classes"
if not os.path.exists(target_class_path):
    os.makedirs(target_class_path)



start_time = time.time() 
result_directory = f'/VESTA/result/{cve_number}/{parent_name}'
target_jar_dir = f"/VESTA/resource/{cve_number}/{child_name}/target/{target_jar_name}"
static_analysis_result = static_analysis("/VESTA", target_jar_dir, result_directory)
static_result = get_static_call_graph(result_directory, target_jar_dir, vul_function_name)
with open(f"{result_directory}/result.txt",'r+') as f:
    f.truncate(0)


# 遍历+generation
projectCP = load_class_path()
target_function_information = ''
test_class_name = ''

with open(f"/VESTA/resource/{cve_number}/{child_name}/info.txt",'r') as f:
    exploit_value = f.readline()
    exploit_value = exploit_value[:-1]
    exploit_value = exploit_value.replace("$","\$")
    

now_logs = choose_call_graph(static_result)
# now_logs[1], now_logs[-1] = now_logs[-1], now_logs[1]
for now_log in now_logs:
    
    test_class_name, target_class_name, target_function_information = get_target_class_name(now_log)
    # generate test
    print(now_log)
    save_info(now_log.split(", ")[-1])
    callee_method = now_log.split(", ")[0].split(":")[0][1:]+','+now_log.split(", ")[0].split(":")[1]
    
    generate_test(target_class_name, projectCP, target_function_information, callee_method)
    
    
    test_name = target_class_name.split('.')[-1]
    last_dot_index = target_class_name.rfind(".")
    test_class_dir = target_class_name[:last_dot_index].replace(".", "/")
    test_name_in_dir = target_class_name.replace(".","/")
    target_test = f"/VESTA/resource/{cve_number}/{child_name}/src/test/java/{test_name_in_dir}_ESTest.java"
    if 'main' in test_name:
        test_names = extract_seed(f'/VESTA/resource/{cve_number}/{child_name}/evosuite-tests/main_ESTest.java', target_function_information.split(':')[1].split('(')[0],target_test)
    else:
        test_names = extract_seed(f'/VESTA/resource/{cve_number}/{child_name}/evosuite-tests/{test_class_dir}/{test_name}_ESTest.java', target_function_information.split(':')[1].split('(')[0],target_test)
    
    print(compile_test(f"/VESTA/resource/{cve_number}/{child_name}"))
    
    if "<init>" in vul_function_name :
        pattern = re.compile(r'\b(\w+)\s*[:<>]?\s*<init>\s*\([^)]*\)')
        vul_API = pattern.findall(vul_function_name)[0]
    else:
        pattern = re.compile(r'\b(\w+)\([^)]*\)')
        vul_API = pattern.findall(vul_function_name)[0]
    
    for test_method_name in test_names:
            
        exploit_result = run_test(cve_number, exploit_value, test_method_name, vul_class_name, vul_API)
        if exploit_result == True:
            end_time = time.time() 
            elapsed_time = end_time - start_time
            print("Done, looking at exploit floder for our result")
            print(f"Time: {elapsed_time}")
            exit()
    
    remove_test_file(target_test)
