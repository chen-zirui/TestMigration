import re
import os


def find_function_calls(test_file_content):
    regex_pattern = r"(@Test.*?public\s+void\s+test\d+\(\)\s*(?:throws\s+Throwable\s*)?{(?:[^{}]*{[^{}]*})*[^{}]*})"
    matches = re.findall(regex_pattern, test_file_content, re.MULTILINE | re.DOTALL)

    # 输出每个测试代码块
    for match in matches:
        print(match)
        print('-' * 20)

    return matches


def construct_test(file_dir, seed, target_test):
    with open(file_dir, 'r') as file:
        java_content = file.read()
    pattern = re.compile(r'import org\.evosuite.*?;', re.DOTALL)
    java_content = re.sub(pattern, '', java_content)
    
    pattern_import_evoassertions = re.compile(r'\s*import static org\.evosuite\.runtime\.EvoAssertions\.\*;[^\n]*\n', re.DOTALL)
    java_content = re.sub(pattern_import_evoassertions, '\n', java_content)
    
    
    pattern_import_evoassertions = re.compile(r'\s*import static org\.evosuite\.shaded\.org\.mockito\.Mockito\.\*;[^\n]*\n', re.DOTALL)
    java_content = re.sub(pattern_import_evoassertions, '\n', java_content)
    
    pattern_runwith = re.compile(r'\s*@RunWith\(EvoRunner\.class\)[^\n]*\n', re.DOTALL)
    java_content = re.sub(pattern_runwith, '\n', java_content)
   

    pattern_extends = re.compile(r'\s*extends[^\n]*\n', re.DOTALL)
    java_content = re.sub(pattern_extends, '{', java_content)
    
    pattern_class_start = re.compile(r'public class [^\{]*\{', re.DOTALL)
    match = re.search(pattern_class_start, java_content)
    
    if match:
        class_start_index = match.end()
        
        # 替换测试类的内容为seed.txt的内容
        java_content = java_content[:class_start_index] + '\n' + "\n".join(seed) + '\n' + '}'

    if not os.path.exists(os.path.dirname(target_test)):
        os.makedirs(os.path.dirname(target_test))
    
    with open(target_test, 'w') as file:
        file.write(java_content)
        
    print("we write!!!"+java_content)
        





def extract_seed(file_dir, function_name, target_test):
    test_method_name = []
    with open(file_dir, 'r') as file:
        java_content = file.read()
    function_calls = find_function_calls(java_content)
    seed = []
    if(function_name == '<init>'):
        function_name = target_test.split('/')[-1].split('_')[0]
        function_name = function_name + '('
        print(function_name)
        
    for test in function_calls:
        if not function_name in test:
            continue
        
        if '\\u' in test:
            continue
        
        # if 'MockFile' in test:
        #     continue
        
        pattern = re.compile(r'try\s*\{(.*?)\}', re.DOTALL)
        matches = re.findall(pattern, test)

        # 保留try块内部的函数实现
        if matches:
            modified_code = matches[0]
        else:
            modified_code = ''

        pattern = re.compile(r'try\s*\{.*?\}', re.DOTALL)
        test_code = re.sub(pattern, '', test)
        pattern = re.compile(r'catch\s*\(.*?\)\s*\{.*?\}', re.DOTALL)
        test_code = re.sub(pattern, modified_code, test_code)
        
        
        
        # some process for mockfile
        test_code = re.sub(r"(?m)^\s*MockFile.*$", "", test_code)
        test_code = re.sub(r"\bmockFile\w*", "null", test_code)
        test_code = re.sub(r"MockFile\.\w+\([^)]*\)", "null", test_code)
        test_code = re.sub(r"MockFile\.\w*", "null", test_code)

        
        pattern = re.compile(r'fail\(.+?\);')
        test_code = re.sub(pattern, '', test_code)
        
        pattern = re.compile(r'assertNotNull\(.+?\);')
        test_code = re.sub(pattern, '', test_code)
        
        pattern = re.compile(r'assertEquals\(.+?\);')
        test_code = re.sub(pattern, '', test_code)
        
        pattern = re.compile(r'assertNull\(.+?\);')
        test_code = re.sub(pattern, '', test_code)
        
        if 'FileSystemHandling' in test_code:
            continue
        
        if 'evoSuiteFile' in test_code:
            continue
        
        if 'MockURL' in test_code:
            continue
        
        if 'mock' in test_code:
            continue
        
        if 'setCurrentTimeMillis' in test_code:
            continue
        
        if 'Random' in test_code:
            continue
        
        

        
        seed.append(test_code)
        print("-------------------")
        print(test_code)
        print("-------------------")
        
        
        test_function_name = re.findall(r'@Test.*?public void (\w+)', test_code, re.DOTALL)
  
        test_method_name.append(test_function_name[0])
        
    construct_test(file_dir, seed, target_test)
    print(test_method_name)
    return test_method_name