import http.server
import os
import socketserver
import random

PORT = 8079
project_dir = os.environ.get('PROJECT_DIR')
LOG_FILE = f'{project_dir}/code/remote-attack-server/access.log'

class RequestHandler(http.server.SimpleHTTPRequestHandler):
    def log_request(self, code='-', size='-'):
        # 随机生成一个 6 位的字符串作为请求 ID
        request_id = ''.join(random.choices('0123456789abcdef', k=6))
        # 将请求 ID、请求行和状态码拼接成一行日志
        log_line = f"[{request_id}] {self.requestline} {code}"
        # 写入日志文件
        with open(LOG_FILE, 'a') as f:
            f.write(log_line + '\n')
        # 调用父类的 log_request 方法记录请求
        super().log_request(code, size)

with socketserver.TCPServer(("", PORT), RequestHandler) as httpd:
    print(f"Serving at port {PORT}")
    httpd.serve_forever()