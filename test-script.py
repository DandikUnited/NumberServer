from socket import socket
import random, requests

for i in range(50):
    data = ""
    for j in range(300000):
        data += str(random.randint(100000000,999999999))+"\n"
    sock = socket()
    sock.connect(('127.0.0.1', 4000))
    sock.sendall(data.encode('utf8'))
