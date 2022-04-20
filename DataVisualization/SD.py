from cmath import sqrt
import pandas
import numpy as np

shortModel = "SchemEx"
year = "2013"
unaryDF  = pandas.read_csv('/home/justinmucke/git/TemporalGraphDifferences/DiffernecesOfSummaries/Results/'+shortModel+'-'+str(year)+'-unaryResults.csv')
results : np.ndarray = unaryDF.values

header = list(unaryDF.columns)
averageSize = results[:, 2]

mean = 0 
for entry in averageSize:
    mean += entry
mean = mean / len(averageSize)

variance = 0
for entry in averageSize:
    variance += (entry - mean)*(entry - mean)
variance = variance / len(averageSize)

deviation = sqrt(variance)

print("Average = " + str(mean))
print("Deviation = " + str(deviation))
    