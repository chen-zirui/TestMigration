import os
from CallGraph import load_class_path

# init project
def init(total_project_dir, target_project_dir):
    command_clean = f"mvn -f {total_project_dir} clean"
    command = f"mvn -f {total_project_dir} install -DskipTests -Dlicense.skip=true"
    command_test = f"mvn -f {target_project_dir} test-compile  -Dlicense.skip=true"

    command_clean_result = os.popen(command_clean).read()
    command_result_test = os.popen(command_test).read()
    command_result = os.popen(command).read()
    
    print(command_clean_result)
    print(command_result)
    print(command_result_test)
    
    
def static_analysis(project_dir, target_jar_dir, result_directory):
    if not os.path.exists(result_directory):
        os.mkdir(result_directory)
    static_tools_dir = f'{project_dir}/code/java-callgraph/target/javacg-0.1-SNAPSHOT-static.jar'
    static_analysis_command = f'java -jar  {static_tools_dir} {target_jar_dir}'
    
    static_analysis_result = os.popen(static_analysis_command).read()
    with open(f'{result_directory}/callLog-{target_jar_dir.split("/")[-1]}.txt', 'w') as f:
        f.write(static_analysis_result)
    

def generate_test(target_class_name, projectCP, caller, callee):
    generation_command = f"$EVOSUITE -class {target_class_name} -projectCP {projectCP} -Dcaller_method=\"{caller}\" -Dcallee_method=\"{callee}\" -Dcarve_object_pool=false -Dsearch_budget=10"
    print(generation_command)
    return os.popen(generation_command).read()


def compile_test(generated_test_dir):
    compiling_generated_test_command = f'mvn test-compile'
    return os.popen(compiling_generated_test_command).read()


def save_info(function_info):
    project_dir = os.environ.get('NOW_DIR')
    with open(f"{project_dir}/info.txt",'r') as f:
        lines = f.readlines()
    with open(f"{project_dir}/info.txt",'w') as f:
        f.write(lines[0][:-1]+'\n')
        f.write(function_info.split(":")[0]+'\n')
        f.write(function_info.split(":")[1].split("(")[0]+'\n')

def load_info():  
    project_dir = os.environ.get('NOW_DIR')
    with open(f"{project_dir}/info.txt",'r') as f:
        lines = f.readlines()
    vul_class = lines[1].strip()
    vul_function = lines[2].strip()
    if "lambda" in vul_function:
        return vul_class,vul_function.split("$")[1]
    return vul_class,vul_function


def generate_command(input,test_name,vul_API,p_position):
    javaagent_position = '/VESTA/tools/migration/target/migration-test.jar'
    class_path = load_class_path()
    vul_class, vul_function = load_info()
    test_class = vul_class+'_ESTest'   
    tmp_input = input.replace("\\","\\\\")
    tmp_input = input.replace("{","\\{")
    return  f'java -javaagent:{javaagent_position}="{test_name}<separate>{vul_class}<separate>{vul_function}<separate>{vul_API}<separate>{tmp_input}<separate>{p_position}" -cp {class_path} org.junit.runner.JUnitCore {test_class}'

def remove_test_file(dir):
    if os.path.exists(dir):
        os.remove(dir)