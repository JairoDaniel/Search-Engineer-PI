import pandas as pd
import json
from esManager import EsManagement

# Read the CSV file with the films-series information
data = pd.read_csv('imdb.csv')

# Keep the needed columns for the index 
fsInfo_df = data[["Name","Rate","Genre"]] 

# Update value from "No Rate" to 0.0
fsInfo_df.loc[fsInfo_df["Rate"] == "No Rate", "Rate"] = 0.0

#Create json from the columns
result = movSerInfo_df.to_json(orient="records")
documentFS = json.loads(result)

#Class to handle Elasticsearch comunication
es_man = EsManagement()

#Open de mapping json file
f = open('./mov_ser_mapping.json')
mapping = json.load(f)
f.close()

#Create the index in Elasticsearch
es_man.create_index("imdb", mapping)

#Populate the index in Elasticsearch
i=0
while(i<len(documentFS)):
    es_man.populate_index("imdb",documentFS[i])
    i+=1