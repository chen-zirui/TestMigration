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
test_class_name = sys.argv[7]
test_class_name_dir = sys.argv[8]

# project name
if parent_name != child_name:
    child_name = f'{parent_name}/{child_name}'

# get project dir from os
project_dir = os.environ.get('PROJECT_DIR')

# # do init process
# init(project_dir)

start_time = time.time()
    
    
# part2 test generation
#get dependency dir (You need to change the CLASSPATH.txt)
projectCP = ""
with open("CLASSPATH.txt",'r') as f:
    projectCP = f.read()

projectCP = projectCP.replace("data0","data1")

print(f'{project_dir}/resource/{cve_number}/{child_name}/target/test-classes/{test_class_name_dir}')


# run test without instrumentor
run_generated_test_command = f'java  -classpath {project_dir}/resource/{cve_number}/{child_name}/target/test-classes:{projectCP} org.junit.runner.JUnitCore {test_class_name}'
run_generated_test_command_result = os.popen(run_generated_test_command).read()


# run test with instrumentor
migration_tools_dir = f'{project_dir}/code/migration/target/migration-test-jar-with-dependencies.jar'
run_generated_test_command = f'java -javaagent:{migration_tools_dir}="pocValue="123";targetClass="123";targetMethod="123";targetCall="123";finalClass="Dataverse.FindingBoundingBoxes.GeonamesJSON";finalMethod="<init>";finalCall="toJSONObject";index=1; -classpath {project_dir}/resource/{cve_number}/{child_name}/target/test-classes:{projectCP} org.junit.runner.JUnitCore {test_class_name}'
run_generated_test_command_result = os.popen(run_generated_test_command).read()


print(run_generated_test_command_result)
end_time = time.time()
elapsed_time = end_time - start_time
