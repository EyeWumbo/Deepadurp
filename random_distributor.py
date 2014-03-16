from random import gauss;

cpuConst = 100;
delayConst = 70;
priorityConst = 10;

def genDistroFromAvgCpuBurst(burstMean, stdDev):
    lst = [];
    for i in range(0, 100):
        lst.append(gauss(burstMean, stdDev));
    return lst;

