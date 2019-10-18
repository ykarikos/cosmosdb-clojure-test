(ns cosmos-test.core
  (:require [rx.lang.clojure.interop :as rx])
  (:import (com.microsoft.azure.cosmosdb ConnectionPolicy ConsistencyLevel FeedOptions)
           (com.microsoft.azure.cosmosdb.rx AsyncDocumentClient))
  (:gen-class))

(defn- do-query [uri master-key]
  (let [client (-> (com.microsoft.azure.cosmosdb.rx.AsyncDocumentClient$Builder.)
                   (.withServiceEndpoint uri)
                   (.withMasterKey master-key)
                   (.withConnectionPolicy (ConnectionPolicy/GetDefault))
                   (.withConsistencyLevel ConsistencyLevel/Session)
                   .build)
        query-options (-> (FeedOptions.)
                          (.setMaxItemCount (int 10)))
        observable (.queryDocuments
                     client
                     "/dbs/ToDoList/colls/Items"
                     "SELECT * FROM c"
                     query-options)]
    (-> observable
        (.subscribe (rx/action [page]
                               (->> (-> page .getResults)
                                    (map #(.toJson %))
                                    println))
                    (rx/action [e] (.printStackTrace e))
                    (rx/action [] (println "Completed"))))))

(defn -main
  [uri master-key]
  (do-query uri master-key))
