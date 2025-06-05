print("Shows all connected devices to network")
#import scapy.all as scapy
#request=scapy.ARP()
#request.pdst='192,168.254.1/24' #ip range from 192.168.254.1 to 192.168.254.254
#boardcast=scapy.Ether()
#boardcast.hwdst="ff:ff:ff:ff:ff:ff"
#packet=request/boardcast
#client=scapy.srp(packet,timeout=3,verbose=0)
#print("Ip"+" "*18+"Mac")
#for sent,received in client:
#   print(f"{received.psrc}"+" "*18+f"{received.hwsrc}")
