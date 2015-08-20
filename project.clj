(defproject ring-core-cn "1.4.0"
  :description "Ring 核心库 中文注释版"
  :url "https://github.com/ring-clojure/ring"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.reader "0.9.1"]
                 [ring/ring-codec "1.0.0"]
                 [commons-io "2.4"]
                 [commons-fileupload "1.3.1"]
                 [clj-time "0.9.0"]
                 [crypto-random "1.2.0"]
                 [crypto-equality "1.0.0"]]
  :profiles {:provided {:dependencies [[javax.servlet/servlet-api "2.5"]]}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}})
