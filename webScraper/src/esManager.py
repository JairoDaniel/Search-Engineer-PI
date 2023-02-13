#Import libraries
import json
import logging
from typing import Dict
import os
import numpy as np
from elasticsearch import Elasticsearch

logging.basicConfig(filename="es.log", level=logging.INFO)

class EsManagement:
    def __init__(self):
        self.es_client = Elasticsearch(
            [
                'https://elastic:elastic@localhost:9200/'
            ],
            # make sure we verify SSL certificates
            verify_certs=True,
            # provide a path to CA certs on disk
            ca_certs='../certs/http_ca.crt',
        )
        logging.info(self.es_client.ping())

    def create_index(self, index_name: str, mapping: Dict) -> None:
        """
        Create an ES index.
        :param index_name: Name of the index.
        :param mapping: Mapping of the index
        """
        logging.info(f"Creating index {index_name} with the following schema: {json.dumps(mapping, indent=2)}")
        self.es_client.indices.create(index=index_name, ignore=400, body=mapping)

    def populate_index(self, index_name: str, doc) -> None:
        """
        Populate an index from a CSV file.
        :param path: The path to the CSV file.
        :param index_name: Name of the index to which documents should be written.
        """
        logging.info(f"Writing {len(doc)} documents to ES index {index_name}")
        self.es_client.index(index=index_name, body=doc)
          