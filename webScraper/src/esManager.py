import json
import logging
from typing import Dict
import os
import numpy as np
from elasticsearch import Elasticsearch

logging.basicConfig(filename="es.log", level=logging.INFO)

"""
Class to interact with Elasticsearch.
"""
class EsManagement:
    
    """
    If you want to use security validation update with the following configuration
    Server: 'https://elastic:elastic@localhost:9200/'
    verify_certs = True
    ca_certs = <path/to/http_ca.crt>
    """
    def __init__(self):
        self.es_client = Elasticsearch(
            [
                'http://localhost:9200/'
            ],
            verify_certs=False
        )
        logging.info(self.es_client.ping())
    
    """
    Create an Elasticsearch index.
    :param index_name: Name of the index.
    :param mapping: Mapping of the index
    """
    def create_index(self, index_name: str, mapping: Dict) -> None:
        logging.info(f"Creating the index {index_name} with the following mapping: {json.dumps(mapping, indent=2)}")
        self.es_client.indices.create(index=index_name, ignore=400, body=mapping)

    """
    Populate an index with document data.
    :param index_name: Name of the index.
    :param path: The path to the CSV file.
    """
    def populate_index(self, index_name: str, doc) -> None:
        logging.info(f"Writing documents to ES index {index_name}")
        self.es_client.index(index=index_name, body=doc)
          