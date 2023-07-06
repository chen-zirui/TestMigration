import sys
import os


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
    
cve_number = sys.argv[1]
parent_name = sys.argv[2]
child_name = sys.argv[3]

if parent_name != child_name:
    child_name = f'{parent_name}/{child_name}'

project_dir = os.environ.get('PROJECT_DIR')

init(project_dir)



# part1 static analysis




# java -jar ./code/java-callgraph/target/javacg-0.1-SNAPSHOT-static.jar  ./code/java-callgraph/target/javacg-0.1-SNAPSHOT-static.jar





# part2 test generation







# part3 instrument






# part4 migration and vulnerability catch




