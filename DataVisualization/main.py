from ast import List
import matplotlib.pyplot as plt
import numpy as np 
import pandas

unaryDF   = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/AC-2019unaryResults.csv')
binaryDF  = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/binaryResults.csv')



year = "2019"
shortModel = "AC"
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
        plt.xlabel('Timestep')
        plt.ylabel('Value')
        plt.savefig('/home/justinmucke/git/TemporalGraphDifferences/DataVisualization/plots/'+ header[metric] + '-shortModel-year')
        plt.show()

visualize(unaryDF, False)
visualize(binaryDF, True)
