
# todo automatic choose
import os
def choose_call_graph(static_result):
    return static_result


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
        
        
def load_class_path():
    project_dir = os.environ.get('NOW_DIR')
    with open(f"{project_dir}/CLASSPATH.txt",'r') as f:
        projectCP = f.read()
    projectCP = projectCP.replace("data0","data1")
    projectCP = projectCP.replace("data0","data1")
    projectCP = projectCP.replace("/data1/ziruichen/TestMigration","/VESTA")
    projectCP = projectCP.replace("/libraryFix","/VESTA")
    projectCP = projectCP.replace("/data1/ziruichen/.m2/repository","/root/.m2/repository")
    projectCP = projectCP.replace(":/.m2",":/root/.m2/repository")
    projectCP = projectCP.replace("code/generator/standalone_runtime/target/evosuite-standalone-runtime-1.0.4.jar","code/generator/standalone_runtime/target/evosuite-standalone-runtime-1.0.6.jar")
    class_path_tools = ':/root/.m2/repository/org/javassist/javassist/3.25.0-GA/javassist-3.25.0-GA.jar:/root/.m2/repository/junit/junit/4.12/junit-4.12.jar:/root/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:'
    projectCP += class_path_tools
    projectCP += f'{project_dir}/target/test-classes'
    
    return projectCP


def get_target_class_name(now_log):
    # get basic info from chain
    target_function_information = now_log.split(', ')[-1][0:-1]
    # 这个逻辑不知道是谁的特判
    if( 'main' in target_function_information and ("CSDNLoginApater" in target_function_information or "HttpUtils" in target_function_information)):                         
        target_function_information = now_log.split(', ')[-2]
    target_class_name = target_function_information.split(':')[0]
    test_class_name = target_class_name.split('$')[0]+"_ESTest"   #这里再一次修改中增加了split部分，不知道是否影响
    return test_class_name, target_class_name, target_function_information


def get_static_call_graph(result_directory, target_jar_dir, vul_function_name):
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
        
        # find and save suitable callgraph
        dfs(static_callgraph, vul_function_name, call_tree, visited, static_result)
        with open(f'{result_directory}/callResult-{target_jar_dir.split("/")[-1]}.txt', 'w') as f:
                f.write("[{}]".format(", ".join(str(x) for x in static_result)))
        return static_result