import random, requests

for i in range(50):
    form-data = ""
    for j in range(160000):
        form-data += '\n'+str(random.randint(1000000000,9999999999))
        requests.post("http://localhost:4000/numbers-form",data={"data":form-data[2:]})
    print(j)
