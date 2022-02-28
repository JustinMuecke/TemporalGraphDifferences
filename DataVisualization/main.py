from ast import List
import matplotlib.pyplot as plt
import numpy as np 
import pandas

unaryDF   = pandas.read_csv('DiffernecesOfSummaries/Results/unaryResults.csv')
binaryDF  = pandas.read_csv('DiffernecesOfSummaries/Results/binaryResults.csv')


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
        plt.savefig('DataVisualization/plots/'+ header[metric] + '-AC-2019')
        plt.show()

visualize(unaryDF, False)
visualize(binaryDF, True)