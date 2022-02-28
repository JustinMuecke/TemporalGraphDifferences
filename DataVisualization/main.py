import matplotlib as mpl
import numpy as np 
import pandas

unaryResults : pandas.DataFrame  = pandas.read_csv('DiffernecesOfSummaries/Results/unaryResults.csv')
binaryResults : pandas.DataFrame = pandas.read_csv('DiffernecesOfSummaries/Results/binaryResults.csv')



for metric in unaryResults:
    unaryResult : List[int] = unaryResults[metric]
    print(unaryResult)

