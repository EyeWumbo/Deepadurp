from random import gauss;
from random import randint;

cpuConst = 100;
delayConst = 70;
priorityConst = 10;

def genDistro(burstMean, const):
    lst = [];
    for i in range(0, 100):
        test = abs(int(gauss(burstMean, const/6)))
        if(test > const - 1):
            test = const - 1;
        lst.append(test);
    return lst;


def SingleQueueGenerator():
    for i in range(30, 80, 40):
        for j in range(2, 8, 5):
            lstCPU = genDistro(i, cpuConst);
            lstPriority = genDistro(j, priorityConst);
            lstDelay = [];
            for k in range(0, 100):
                lstDelay.append(randint(0, delayConst-1));
            file = open("./data/os_test_SQ_" + str(i) + "_" + str(j) + ".dat", 'w');
            for k in range(0, 100):
                file.write(str(lstCPU[k]) + " " + str(lstDelay[k]) + " " + str(lstPriority[k]) + "\n");
            file.close();


def algorithmGenerator(filename, start, end, skip):
    for i in range(start, end, skip):
        lstCPU = genDistro(i, cpuConst);
        lstDelay = [];
        lstPriority = [];
        for k in range(0, 100):
            lstDelay.append(randint(0, delayConst));
            lstPriority.append(randint(0, priorityConst));
        file = open("./data/os_test_" + filename + "_" + str(i) + ".dat", 'w');
        for k in range(0, 100):
            file.write(str(lstCPU[k]) + " " + str(lstDelay[k]) + " " + str(lstPriority[k]) + "\n");
        file.close();
        
algorithmGenerator("FCFS", 20, 100, 20);
algorithmGenerator("SJF", 20, 100, 20);
algorithmGenerator("RR", 20, 90, 30);
SingleQueueGenerator();
