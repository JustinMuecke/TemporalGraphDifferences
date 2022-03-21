from ast import List
from unittest import skip
import matplotlib.pyplot as plt
import numpy as np 
import pandas




def visualize(dataframe : pandas.DataFrame, skipSecond: bool):
    header = list(dataframe.columns)
    results : np.ndarray = dataframe.values

    columns : int = results.shape[1]
    rows : int = results.shape[0]

    xval = [i for i in range(rows)]


    for metric in range(columns):
        if(metric == 0):
            continue
        if(skipSecond):
            if(metric==1):
                continue


        values = results[:, metric]
        

        plt.plot(xval, values)
        plt.title(header[metric])


       
        if(not skipSecond):
            if(metric == 2):
                referenceValues=results[:, 1]/120
                plt.plot(xval, referenceValues)

            if(metric == 4):
                referenceValues=results[:,3]*3000
                plt.plot(xval, referenceValues)

        plt.xlabel('Timestep')
        plt.ylabel('Value')
        plt.savefig('/home/justinmucke/git/TemporalGraphDifferences/DataVisualization/plots/'+shortModel +'/' + str(year) +'/'+ header[metric])
        plt.show()
    return results

unaryResults : np.ndarray = np.array([[]])
unaryResults.shape=(0, 6)
binaryResults : np.ndarray = np.array([[]])
binaryResults.shape=(0, 6)

shortModel = "SchemEx"
first : int = 2013
last : int= 2014
for year in range(first, last +1):
    unaryDF   = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/'+shortModel+'-'+str(year)+'-unaryResults.csv')
    unaryResults = np.concatenate((unaryResults, visualize(unaryDF, False)), 0)
    binaryDF  = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/'+shortModel+'-'+str(year)+'-binaryResults.csv')
    binaryResults = np.concatenate((binaryResults, visualize(binaryDF, True)), 0)


       
xval = [i for i in range(unaryResults.shape[0])]
unaryHeader = list(unaryDF.columns)

for metric in range(6):
    if(metric == 0): 
        continue
    unaryVals = unaryResults[:, metric]
    plt.plot(xval, unaryVals)
    plt.title(unaryHeader[metric])
    plt.xlabel('Timestep')
    plt.ylabel('Value')
    plt.savefig('/home/justinmucke/git/TemporalGraphDifferences/DataVisualization/plots/'+ shortModel + '/' + str(unaryHeader[metric]))
    plt.show()



xval = [i for i in range(binaryResults.shape[0])]
binaryHeader = list(binaryDF.columns)

for metric in range(6):
    if(metric == 0 or metric == 1): 
        continue
    binaryVals = binaryResults[:, metric]
    plt.plot(xval, binaryVals)
    plt.title(binaryHeader[metric])
    plt.xlabel('Timestep')
    plt.ylabel('Value')
    print(binaryHeader[metric])
    plt.savefig('/home/justinmucke/git/TemporalGraphDifferences/DataVisualization/plots/'+ shortModel + '/' + str(binaryHeader[metric]))
    plt.show()

