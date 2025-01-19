(ns koans.19-datatypes
  (:require [koan-engine.core :refer :all]))

;; clojure 里的 defrecord 和 deftype defprotocol 的区别和联系是什么？


;; `defrecord`、`deftype` 和 `defprotocol` 是 Clojure 中用于定义数据结构和行为的核心构造。它们之间有以下区别和联系：

;; 1. **defrecord**
;;    - `defrecord` 用于定义一个具有命名字段的 "record" 类型。
;;    - 它为每个字段生成getter和setter函数。
;;    - 实现了 `map` 接口，可以使用 map 函数来访问字段。
;;    - 提供了一些基本的功能，如哈希码、相等性测试和可打印性。

;; 2. **deftype**
;;    - `deftype` 用于定义一个新的抽象数据类型。
;;    - 它直接生成 Java 类，因此性能更高。
;;    - 不支持字段访问器和元数据。
;;    - 常用于实现高效的数据结构和算法。

;; 3. **defprotocol**
;;    - `defprotocol` 用于定义一个协议（Protocol），类似于Java中的接口。
;;    - 协议定义了一组函数签名，但不提供实现。
;;    - 使用 `defrecord` 或 `deftype` 定义的类型可以提供协议的实现。
;;    - 协议支持多方法分派（多个arities）。

;; 这三者的联系在于，它们都是用于构建数据结构和行为的抽象。`defrecord` 和 `deftype` 定义了具体的数据类型，而 `defprotocol` 则定义了一组通用的操作接口。

;; 通常情况下，我们使用 `defrecord` 来定义不可变的数据结构，使用 `deftype` 来实现性能关键的数据结构和算法，使用 `defprotocol` 来抽象出通用的操作接口，并由 `defrecord` 或 `deftype` 定义的类型来实现协议。

;; 这种设计模式使得 Clojure 程序具有很好的模块化和可扩展性。数据结构和行为可以分开定义和组合，从而提高代码的可重用性和可维护性。

(defrecord Nobel [prize])
(deftype Pulitzer [prize])

(defprotocol Award
  (present [this recipient]))

(defrecord Oscar [category]
  Award
  (present [this recipient]
    (print (str "Congratulations on your "
                (:category this) " Oscar, "
                recipient
                "!"))))

(deftype Razzie [category]
  Award
  (present [this recipient]
    (print (str "You're really the "
                (.category this) ", "
                recipient "... sorry."))))

(meditations
  "Holding records is meaningful only when the record is worthy of you"
  (= "peace" (.prize (Nobel. "peace")))

  "Types are quite similar"
  (= "literature" (.prize (Pulitzer. "literature")))

  "Records may be treated like maps"
  (= "physics" (:prize (Nobel. "physics")))

  "While types may not"
  (= nil (:prize (Pulitzer. "poetry")))

  "Further study reveals why"
  (= [true false]
     (map map? [(Nobel. "chemistry")
                (Pulitzer. "music")]))

  "Either sort of datatype can define methods in a protocol"
  (= "Congratulations on your Best Picture Oscar, Evil Alien Conquerors!"
     (with-out-str (present (Oscar. "Best Picture") "Evil Alien Conquerors")))

  "Surely we can implement our own by now"
  (= "You're really the Worst Picture, Final Destination 5... sorry."
     (with-out-str (present (Razzie. "Worst Picture") "Final Destination 5"))))
