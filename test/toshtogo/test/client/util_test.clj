(ns toshtogo.test.client.util-test
  (:import [toshtogo.client.senders SenderException]
           (java.util.concurrent ExecutionException))
  (:require [midje.sweet :refer :all]
            [flatland.useful.map :refer [into-map]]
            [toshtogo.client.util :refer [throw-500 merge-dependency-results]]))

(fact "throw-500 works"
      (throw-500 {:status 500}) => (throws SenderException)
      (throw-500 {:status 200}) => {:status 200}
      (throw-500 nil) => nil)

(fact "Merge dependency results into request"
      (merge-dependency-results {:request_body {:some-value 1}
                                 :dependencies [{:job_type    "dependency_one"
                                                 :result_body {:dep1-value 1}}
                                                {:job_type    "dependency_two"
                                                 :result_body {:dep2-value 1}}]})
      => (just {:some-value  1
                :dependency_one  {:dep1-value 1}
                :dependency_two {:dep2-value 1}}))

(fact "Merge multiple dependency results into request where key already exists"
      (merge-dependency-results {:request_body {:some-value 1
                                                :child_job_type [{:dep0-value 0}]}
                                 :dependencies [{:job_type    :child_job_type
                                                 :result_body {:dep1-value 1}}
                                                {:job_type    :child_job_type
                                                 :result_body {:dep2-value 2}}]}
                                :merge-multiple [:child_job_type])
      => (just {:some-value  1
                :child_job_type (contains [{:dep0-value 0}
                                           {:dep1-value 1}
                                           {:dep2-value 2}])}))
