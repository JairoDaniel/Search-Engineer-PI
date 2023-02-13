#Import libraries
import pandas as pd
import json
from esManager import EsManagement

# Read the CSV file with the movies information
data = pd.read_csv('imdb.csv')

# Keep the needed columns for the index 
df2 = data[["Name","Rate","Genre"]] 

# Update value from No Rate to 0.0
df2.loc[df2["Rate"] == "No Rate", "Rate"] = 0.0


#Create json from the columns
result = df2.to_json(orient="records")
parsed = json.loads(result)

es_man = EsManagement()

f = open('./mov_ser_mapping.json')
mapping = json.load(f)
f.close()

es_man.create_index("imdb", mapping)

i=0
while(i<len(parsed)):
    es_man.populate_index("imdb",parsed[i])
    i+=1