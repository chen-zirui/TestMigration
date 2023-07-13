import sys
import os
import glob
import re

# part0 init project
def init(project_dir):
    total_dir = f"{project_dir}/resource/{cve_number}/{parent_name}"
    target_dir = f"{project_dir}/resource/{cve_number}/{child_name}"

    command_clean = f"mvn -f {total_dir} clean"
    command = f"mvn -f {total_dir} install -DskipTests"
    command_test = f"mvn -f {target_dir} test-compile"

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
# package_name = sys.argv[4]
# target_method_name = sys.argv[5]

# project name
if parent_name != child_name:
    child_name = f'{parent_name}/{child_name}'

# get project dir from os
project_dir = os.environ.get('PROJECT_DIR')

#do init process, like maven install
init(project_dir)




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
target_dir = f"{project_dir}/resource/{cve_number}/{child_name}/target"
jar_files = glob.glob(os.path.join(target_dir, "*.[jw]ar"))
# write log to file
for jar_file in jar_files:
    static_tools_dir = f'{project_dir}/code/java-callgraph/target/javacg-0.1-SNAPSHOT-static.jar'
    static_analysis_command = f'java -jar  {static_tools_dir} {jar_file}'
    static_analysis_result = os.popen(static_analysis_command).read()
    with open(f'{result_directory}/callLog-{jar_file.split("/")[-1]}.txt', 'w') as f:
        f.write(static_analysis_result)
    # check
    with open(f'{result_directory}/callLog-{jar_file.split("/")[-1]}.txt', 'r') as f:
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
        static_result = []
        dfs(static_callgraph, vul_function_name, call_tree, visited, static_result)
        
        with open(f'{result_directory}/callResult-{jar_file.split("/")[-1]}.txt', 'w') as f:
             f.write("[{}]".format(", ".join(str(x) for x in static_result)))
        

# part2 test generation


#get dependency dir
# def get_package_position(project_name):
#     total_dir = f"{project_dir}/resource/{cve_number}/{parent_name}"
#     target_dir = f"{project_dir}/resource/{cve_number}/{child_name}"
#     command = f"mvn -f {target_dir} dependency:build-classpath  -DincludeScope=compile"
#     command_result = os.popen(command).read()
    
#     command = f"mvn -f {total_dir} dependency:build-classpath  -DincludeScope=compile"
#     command_result_total = os.popen(command).read()
    
#     pattern = r'Dependencies classpath:\n(.*)\n'
#     match = re.search(pattern, command_result)
#     match_total = re.search(pattern, command_result_total)
#     if not match:
#         raise ValueError('无法解析依赖路径')
#     path = match.group(1).replace('\\', '/')
#     # path += ":"
#     # path+= match_total.group(1).replace('\\', '/') 
    
#     return path

# package_postion = get_package_position(child_name)
# elements = package_postion.split(":")
# result = ""
# for element in elements:
#     # 从右侧查找第一个出现的/或.字符
#     slash_index = element.rfind("/")
#     dot_index = element.rfind(".")
    
#     # 如果找到了/或.字符
#     if slash_index != -1 or dot_index != -1:
#         # 获取/或.字符右侧的子字符串
#         if slash_index > dot_index:
#             extension = element[slash_index:]
#         else:
#             extension = element[dot_index:]
        
#         # 如果子字符串为.jar或/，则保留它
#         if extension == ".jar" or extension == "/":
#             result += element + ":"
#     else:
#         result += element + ":"

# # 删除最后一个冒号
# if result.endswith(":"):
#     result = result[:-1]

# package_postion = result

# generation_command = f"$EVOSUITE  -class org.superbiz.moviefun.utils.TokenUtil -projectCP {project_dir}/resource/{cve_number}/{child_name}/target/classes:{package_postion}"
# print(generation_command)
# generation_command_result = os.popen(generation_command).read()

#get dependency dir (You need to change the RUN.sh)
generation_command_dir = f'{project_dir}/resource/{cve_number}/{parent_name}/RUN.sh'
generation_command = f'bash {generation_command_dir}'
generation_command_result = os.popen(generation_command).read()


# part3 test migration




# part4 migration and vulnerability catch

