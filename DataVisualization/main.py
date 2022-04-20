from ast import List
import matplotlib.pyplot as plt
import numpy as np 
import pandas




def visualize(dataframe : pandas.DataFrame, skipSecond: bool):
    header = list(dataframe.columns)
    results : np.ndarray = dataframe.values

    columns : int = results.shape[1]
    rows : int = results.shape[0]

    xval = [i for i in range(rows)]

    for metric in range(2,4):
        if(metric == 0):
            continue
        if(skipSecond):
            if(metric==1):
                continue

        values = results[:, metric]

        plt.plot(xval, values, color = 'blue')
        plt.xlabel('Timestep')
        plt.ylabel(header[metric], color = 'blue')

        if(not skipSecond):
            if(metric == 2):
                ax2 = plt.twinx()
                referenceValues=results[:, 1]
                ax2.plot(xval, referenceValues, linestyle="dotted", color='orange')
                ax2.set_ylabel(header[1], color = 'orange')

            if(metric == 4):
                ax2 = plt.twinx()
                referenceValues=results[:,3]
                ax2.plot(xval, referenceValues, linestyle="dotted", color='orange')
                ax2.set_ylabel(header[3], color='orange')
        plt.savefig('/home/justinmucke/git/TemporalGraphDifferences/DataVisualization/plots/'+shortModel +'/' + str(year) +'/'+ header[metric], bbox_inches='tight')
        plt.tight_layout()
        plt.show()
       
    return results

unaryResults : np.ndarray = np.array([[]])
unaryResults.shape=(0, 7)
binaryResults : np.ndarray = np.array([[]])
binaryResults.shape=(0, 6)

shortModel = "SchemEx"
first : int = 2013
last : int= 2013
for year in range(first, last +1):
    #unaryDF   = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/'+shortModel+'-'+str(year)+'-unaryResults.csv')
    #unaryResults = np.concatenate((unaryResults, visualize(unaryDF, False)), 0)
    binaryDF  = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/'+shortModel+'-'+str(year)+'-binaryGEDResults.csv')
    binaryResults = np.concatenate((binaryResults, visualize(binaryDF, True)), 0)






xval = [i for i in range(binaryResults.shape[0])]
binaryHeader = list(binaryDF.columns)

for metric in range(2,4):
    if(metric == 0 or metric == 1): 
        continue
    binaryVals = binaryResults[:, metric]
    plt.plot(xval, binaryVals, color='blue')
    plt.xlabel('Timestep')
    plt.ylabel(binaryHeader[metric])
    plt.tight_layout()
    plt.savefig('/home/justinmucke/git/TemporalGraphDifferences/DataVisualization/plots/'+ shortModel + '/' + str(binaryHeader[metric]).replace(" ", ""))
    plt.show()



       
