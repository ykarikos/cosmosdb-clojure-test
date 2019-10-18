# Azure CosmosDB Clojure proof of concept

Try out [Azure CosmosDB](https://azure.microsoft.com/en-us/free/cosmos-db/)'s [Java SDK](https://github.com/Azure/azure-cosmosdb-java) on [Clojure](https://clojure.org).
Uses [RxClojure](https://github.com/ReactiveX/RxClojure) too.

## Setup

1. Create a new free CosmosDB account in [Azure](https://azure.microsoft.com/en-us/free/cosmos-db/)
2. Create a new database and container in the account. I used the default database `ToDoList` and container `Items`
3. Add some data in [Azure Portal](https://portal.azure.com) → CosmosDB → Data explorer
4. Check your CosmosDB Instance *URI* and *Primary Key* in Azure Portal → CosmosDB → Keys
5. Run the Clojure program
6. ???
7. Profit!

## Usage

```
$ lein run https://your-cosmosdb-account.documents.azure.com:443/" "your-cosmos-primary-key"
```

## Helpful Documentation

- Azure CosmosDB [Java SDK](https://github.com/Azure/azure-cosmosdb-java) 
- [Java SDK Javadoc](https://azure.github.io/azure-cosmosdb-java/2.4.0/index.html?com/microsoft/azure/cosmosdb/rx/AsyncDocumentClient.html)
- [Java SDK Example](https://github.com/Azure-Samples/azure-cosmos-db-sql-api-async-java-getting-started/blob/master/azure-cosmosdb-get-started/src/main/java/com/microsoft/azure/cosmosdb/sample/Main.java)
- [RxJava Javadoc](http://reactivex.io/RxJava/1.x/javadoc/rx/Observable.html#subscribe-rx.functions.Action1-)
- [Clojure Java Interop](https://clojure.org/reference/java_interop)
- [RxClojure](https://github.com/ReactiveX/RxClojure)

## License

Copyright (c) 2019 Yrjö Kari-Koskinen

Azure CosmosDB Clojure proof of concept source code is licensed with the MIT License, see [LICENSE](LICENSE).
