import re

def check_exception(exception, content):
    if(exception in content):
        return True
    else:
        return False
    
    
def get_remote_log_number():
    with open(f"/VESTA/code/remote-attack-server/number.txt", "r") as f:
       number = f.readline()
    return int(number)
    
    
def check_remote(behavior, content):
    remote_log_number = get_remote_log_number()
    with open(f"/VESTA/code/remote-attack-server/access.log", "r") as f:
        line_count = 0
        for line in f:
            line_count += 1
        if line_count > remote_log_number:
            with open(f"/VESTA/code/remote-attack-server/number.txt", "w") as f:
                f.write(str(line_count))
            return True
    return False


def check_behavior(behavior, content):
    return False


def check_no_exception(behavior, content):
    exploit = behavior.split(';')[0]
    execption_name = behavior.split(';')[1]
    pattern = r"Class: (\S+), Method: (\S+), Parameter (\d+) Type: (\S+), Value: (.+)"
    matches = re.finditer(pattern, content)
    for match in matches:
        parameter_value = match.group(5)
        if parameter_value == exploit and execption_name not in content:
            return True
    return False



def is_trigger(category, behavior, content):
    if("exception" == category):
        return check_exception(behavior, content)
    elif("remote" == category):
        return check_remote(behavior, content)
    elif("behavior" == category):
        return check_behavior(behavior, content)
    elif("no-exception" == category):
        return check_no_exception(behavior, content)
    else:
        return False