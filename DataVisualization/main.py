from ast import List
from unittest import skip
import matplotlib.pyplot as plt
import numpy as np 
import pandas

year = "2014"
shortModel = "AC"

unaryDF   = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/'+shortModel+'-'+year+'-unaryResults.csv')
binaryDF  = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/'+shortModel+'-'+year+'-binaryResults.csv')



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
        plt.savefig('/home/justinmucke/git/TemporalGraphDifferences/DataVisualization/plots/'+shortModel +'/' + year +'/'+ header[metric])
        plt.show()

visualize(unaryDF, False)
visualize(binaryDF, True)
