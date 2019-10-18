(defproject cosmos-test "0.1.0-SNAPSHOT"
  :description "Azure CosmosDB Clojure proof of concept"
  :license {:name "MIT License"
            :url "https://github.com/ykarikos/cosmosdb-clojure-test/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.microsoft.azure/azure-cosmosdb "2.6.2"]
                 [io.reactivex/rxclojure "1.0.0"]]
  :main ^:skip-aot cosmos-test.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
