; Ported the executeSimpleQueryAsyncAndRegisterListenerForResult method to Clojure from the Java example at
; https://github.com/Azure-Samples/azure-cosmos-db-sql-api-async-java-getting-started/blob/master/azure-cosmosdb-get-started/src/main/java/com/microsoft/azure/cosmosdb/sample/Main.java
(ns cosmos-test.core
  (:require [rx.lang.clojure.interop :as rx])
  (:import (com.microsoft.azure.cosmosdb ConnectionPolicy ConsistencyLevel FeedOptions)
           (com.microsoft.azure.cosmosdb.rx AsyncDocumentClient$Builder)
           (java.util.concurrent CountDownLatch))
  (:gen-class))

(defn- do-query [client collection-link query result-fn completion-latch]
  (let [query-options (-> (FeedOptions.)
                          (.setMaxItemCount (int 10)))
        observable (.queryDocuments client collection-link query query-options)]
    (-> observable
        (.subscribe (rx/action [page]
                               (result-fn (-> page .getResults)))
                    (rx/action [e]
                               (.printStackTrace e)
                               (.countDown completion-latch))
                    (rx/action []
                               (println "Completed")
                               (.countDown completion-latch))))))

(defn -main
  [uri master-key]
  (let [query-completion-latch (CountDownLatch. 1)
        client (-> (AsyncDocumentClient$Builder.)
                   (.withServiceEndpoint uri)
                   (.withMasterKey master-key)
                   (.withConnectionPolicy (ConnectionPolicy/GetDefault))
                   (.withConsistencyLevel ConsistencyLevel/Session)
                   .build)]
    (do-query client
              "/dbs/ToDoList/colls/Items"
              "SELECT * FROM c"
              (fn [doc] (->> doc
                             (map #(.toJson %))
                             println))
              query-completion-latch)
    (.await query-completion-latch)
    (.close client)))

