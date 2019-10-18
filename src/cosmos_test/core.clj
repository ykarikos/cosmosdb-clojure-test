(ns cosmos-test.core
  (:require [rx.lang.clojure.interop :as rx])
  (:import (com.microsoft.azure.cosmosdb ConnectionPolicy ConsistencyLevel FeedOptions)
           (com.microsoft.azure.cosmosdb.rx AsyncDocumentClient)
           (java.util.concurrent CountDownLatch))
  (:gen-class))

(defn- do-query [client completion-latch]
  (let [query-options (-> (FeedOptions.)
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
                    (rx/action [e]
                               (.printStackTrace e)
                               (.countDown completion-latch))
                    (rx/action []
                               (println "Completed")
                               (.countDown completion-latch))))))

(defn -main
  [uri master-key]
  (let [query-completion-latch (CountDownLatch. 1)
        client (-> (com.microsoft.azure.cosmosdb.rx.AsyncDocumentClient$Builder.)
                   (.withServiceEndpoint uri)
                   (.withMasterKey master-key)
                   (.withConnectionPolicy (ConnectionPolicy/GetDefault))
                   (.withConsistencyLevel ConsistencyLevel/Session)
                   .build)]
    (do-query client query-completion-latch)
    (.await query-completion-latch)
    (.close client)))

