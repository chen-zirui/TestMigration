import subprocess
import re
import random
import difflib
import string
import json
from Catcher import is_trigger
from Command import generate_command

category = ""
behavior = ""


def blacktest(input,test_name,vul_class_name,vul_API,p_position):
    try:
        print("Our input:", input)
        java_command = generate_command(input,test_name,vul_API,p_position)
        print(java_command)
        try:
            result = subprocess.run(java_command, stdout=subprocess.PIPE, timeout=10, stderr=subprocess.PIPE, text=True, shell=True)
        except Exception as e:
            output = e
            print("----------------------------------time out--------------------------------")
            return "<triggerFlag>"
            
        output = result.stdout
        error = result.stderr
    
        # 打印输出和错误
        print("Output:", output)
        # print("Error:", error)
        
        
        global category
        global behavior
        
        if is_trigger(category,behavior, output):
            print("----------------------------------reproduce--------------------------------")
            return "<triggerFlag>"
        
        pattern = r"Class: (\S+), Method: (\S+), Parameter (\d+) Type: (\S+), Value: (.+)"
        matches = re.finditer(pattern, output)
        for match in matches:
            # 提取匹配的信息
            method_class = match.group(1)
            method_name = match.group(2)
            parameter_number = match.group(3)
            parameter_type = match.group(4)
            parameter_value = match.group(5)
            if method_class==vul_class_name:
                print("Method Class:", method_class)
                print("Method Name:", method_name)
                print("Parameter Number:", parameter_number)
                print("Parameter Type:", parameter_type)
                print("Parameter Value:", parameter_value)
                print("---")
                return parameter_value
        
        return None
    
    except subprocess.CalledProcessError as e:
        print("Error:", e)
        
        
def run_test( target_cve_number, target_output, test_name, vul_class_name, vul_API, max_iterations=10):
    with open('/VESTA/vesta/performance.json', 'r') as file:
        data = json.load(file)
    
    global category
    global behavior
        
    # 遍历每个JSON对象
    for item in data:
        # 提取CVE编号
        cve_number = item['CVE_Number']
        # 检查是否与目标CVE编号匹配
        if cve_number == target_cve_number:
            # 提取相应的内容
            category = item['Category']
            behavior = item['Behavior']
            
            # 打印内容
            print("CVE Number:", cve_number)
            print("Category:", category)
            print("Behavior:", behavior)
            break  # 找到匹配的CVE后可以跳出循环
        
    
    p_position = 1
    for iteration in range(max_iterations):
        output = blacktest(target_output,test_name,vul_class_name, vul_API,p_position)
        if output == "<triggerFlag>":
            return True
    return False
        
# Output: test2<separate>com.xebia.lottery.domain.eventstore.XStreamEventSerializer<separate>deserialize<separate>fromXML<separate><void/><separate>1
# Output: test0<separate>com.xebia.lottery.domain.eventstore.XStreamEventSerializer<separate>deserialize<separate>fromXML<separate><void/><separate>1