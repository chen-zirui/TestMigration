import sys
import os
import time

# part0 init project
def init(project_dir):
    total_project_dir = f"{project_dir}/resource/{cve_number}/{parent_name}"
    target_project_dir = f"{project_dir}/resource/{cve_number}/{child_name}"

    command_clean = f"mvn -f {total_project_dir} clean"
    command = f"mvn -f {total_project_dir} install -DskipTests -Dlicense.skip=true"
    command_test = f"mvn -f {target_project_dir} test-compile  -Dlicense.skip=true"

    command_clean_result = os.popen(command_clean).read()
    command_result_test = os.popen(command_test).read()
    command_result = os.popen(command).read()
    
    print(command_clean_result)
    print(command_result)
    print(command_result_test)

# get basic information from sh command
cve_number = sys.argv[1]
parent_name = sys.argv[2]
child_name = sys.argv[3]
vul_function_name = sys.argv[4]
target_jar_name = sys.argv[5]
vulner_show = sys.argv[6]


# project name
if parent_name != child_name:
    child_name = f'{parent_name}/{child_name}'

# get project dir from os
project_dir = os.environ.get('PROJECT_DIR')

# # do init process, like maven install
init(project_dir)


start_time = time.time()

# part1 static analysis
def dfs(call_graph, target_func, call_tree, visited, static_result):
    visited.add(target_func)
    if target_func not in call_graph:
        static_result.append("[{}]".format(", ".join(str(x) for x in call_tree)))
        return
    for callee in call_graph[target_func]:
        call_tree.append(callee)
        if callee not in visited:
            dfs(call_graph, callee, call_tree, visited, static_result)
        else:
            static_result.append("[{}]".format(", ".join(str(x) for x in call_tree)))
            call_tree.pop()
            return
        call_tree.pop()
                
#create result dir
result_directory = f'{project_dir}/result/{cve_number}/{parent_name}'
if not os.path.exists(result_directory):
    os.mkdir(result_directory)
    
# static analysis jar package
target_jar_dir = f"{project_dir}/resource/{cve_number}/{child_name}/target/{target_jar_name}"

# write log to file
static_tools_dir = f'{project_dir}/code/java-callgraph/target/javacg-0.1-SNAPSHOT-static.jar'
static_analysis_command = f'java -jar  {static_tools_dir} {target_jar_dir}'
static_analysis_result = os.popen(static_analysis_command).read()
with open(f'{result_directory}/callLog-{target_jar_dir.split("/")[-1]}.txt', 'w') as f:
    f.write(static_analysis_result)
# check

static_result = []
with open(f'{result_directory}/callLog-{target_jar_dir.split("/")[-1]}.txt', 'r') as f:
    static_callgraph = {}
    for line in f:
        if line.startswith("M"):
            caller = line.split()[0][2:]
            callee = line.split()[1][3:]
            if callee not in static_callgraph:
                static_callgraph[callee] = []
            static_callgraph[callee].append(caller)

    visited = set()
    call_tree = []
    call_tree.append(vul_function_name)
    
    
    dfs(static_callgraph, vul_function_name, call_tree, visited, static_result)
    
    with open(f'{result_directory}/callResult-{target_jar_dir.split("/")[-1]}.txt', 'w') as f:
            f.write("[{}]".format(", ".join(str(x) for x in static_result)))
    
    
# part2 test generation
#get dependency dir (You need to change the CLASSPATH.txt)
projectCP = ""
with open("CLASSPATH.txt",'r') as f:
    projectCP = f.read()

projectCP = projectCP.replace("data0","data1")

# choose 1 call garph


# target_function_information = "com.esri.geoportal.base.util:unescapeNunmericEntity(java.lang.String)".split(', ')[-1][0:-1]
target_function_information = static_result[0].split(', ')[-1][0:-1]
if( 'main' in target_function_information):                         
    target_function_information = static_result[-1].split(', ')[-2]
target_class_name = target_function_information.split(':')[0]
last_dot_index = target_class_name.rfind(".")
test_class_dir = target_class_name[:last_dot_index].replace(".", "/")
test_class_name = target_class_name.split('$')[0]+"_ESTest"   #这里再一次修改中增加了split部分，不知道是否影响

print(test_class_name)
parameterList = target_function_information.split(':')[1].split('(')[1][0:-1].split(',')
print(parameterList)
pocValue = ""
paraPosition = ""
typeCode = ""
with open("info.txt",'r') as f:
    pocValue = f.readline()
    paraPosition = f.readline()
    typeCode = f.readline()
    
    
with open("info.txt",'w') as f:
    f.write(pocValue)
    f.write(paraPosition)
    f.write(typeCode)
    f.write(target_class_name.replace(".","/")+'\n')
    f.write(target_function_information.split(':')[1].split('(')[0]+'\n')
    f.write(parameterList[len(parameterList)-int(paraPosition)])

# generate test
generation_command = f"$EVOSUITE -class {target_class_name} -projectCP {projectCP} -Dsearch_budget=10"
print(generation_command)
generation_command_result = os.popen(generation_command).read()

remote_log_number = 0
with open(f"{project_dir}/code/remote-attack-server/access.log", "r") as f:
    remote_log_number = 0
    for line in f:
        remote_log_number += 1

# compile class
generated_test_dir = f'{project_dir}/resource/{cve_number}/{child_name}/evosuite-tests/{test_class_dir}/*.java'
compiling_generated_test_command = f'javac -classpath {projectCP} {generated_test_dir}'
compiling_generated_test_command_result = os.popen(compiling_generated_test_command).read()


# run test with parameter
run_generated_test_command = f'java  -classpath {project_dir}/resource/{cve_number}/{child_name}/evosuite-tests:{projectCP} org.junit.runner.JUnitCore {test_class_name}'
run_generated_test_command_result = os.popen(run_generated_test_command).read()

print(run_generated_test_command_result)
end_time = time.time()
elapsed_time = end_time - start_time


with open(f"{result_directory}/result.txt",'w') as f:
    f.write(run_generated_test_command_result)
# Remote code execution
if vulner_show == "remote":
    with open(f"{project_dir}/code/remote-attack-server/access.log", "r") as f:
        line_count = 0
        for line in f:
            line_count += 1
        if line_count > remote_log_number:
            print("PocValue is "+ pocValue)
            print("Reproduce!!! Looking at result.txt for more details")
            print("Total time: ",elapsed_time," s")
# Without some key words
elif vulner_show.startswith('-'):     
    if vulner_show not in run_generated_test_command_result:
        print("PocValue is "+ pocValue)
        print("Reproduce!!! Looking at result.txt for more details")
        print("Total time: ",elapsed_time," s")
elif 'JSONObject' in parameterList[len(parameterList)-int(paraPosition)]:
    print("PocValue is "+ pocValue)
    print("Manual repalce the object as 'jsonObject = (JSONObject) parser.parse(PocValue);' !!!")
    print("Function is "+ target_function_information)
    print("Total time: ",elapsed_time," s")
elif 'manual' in vulner_show:
    print("PocValue is "+ pocValue)
    print("Please manual check!!! Looking at result.txt for more details")
    print("Function is "+ target_function_information)
    print("Total time: ",elapsed_time," s")
# With some key words
elif vulner_show in run_generated_test_command_result:
    print("PocValue is "+ pocValue)
    print("Reproduce!!! Looking at result.txt for more details")
    print("Total time: ",elapsed_time," s")
    


# migration_tools_dir = f'{project_dir}/code/migration/target/migration-test-jar-with-dependencies.jar'
# migration_command = f'java -javaagent:{migration_tools_dir}="pocValue="123";targetClass="123";targetMethod="123";targetCall="123";finalClass="Dataverse.FindingBoundingBoxes.GeonamesJSON";finalMethod="<init>";finalCall="toJSONObject";'
# migration_command += 'index=1;"'
# migration_command += f' -cp "{projectCP}:{project_dir}/resource/{cve_number}/{child_name}/evosuite-tests" org.junit.runner.JUnitCore {test_class_name}'
# print(migration_command)
# run_migration_command_result = os.popen(migration_command).read()
# with open("tmp.txt",'w') as f:
#     f.write(run_migration_command_result)