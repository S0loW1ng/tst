import socket
import sys
class TCPServer:
    def __init__(self,address,port):
        self.address=address
        self.port=port
        self.sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)



    def accept(self):
        self.sock.bind((self.address,self.port))
        self.sock.listen(1)
        while True:
            print (sys.stderr, 'waiting for a connection')
            connection, client_address = self.sock.accept()
            try:
                print ( sys.stderr, 'client connected:', client_address)
                while True:
                    data = connection.recv(1000000)
                    print (sys.stderr, 'received "%s"' % data)
                    if data:
                        connection.sendall(data)
                    else:
                        break
            finally:
                connection.close()


server = TCPServer('localhost',9998)
server.accept()





