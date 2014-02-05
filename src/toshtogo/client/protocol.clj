(ns toshtogo.client.protocol
  (:require [toshtogo.server.api.protocol :as server-protocol]))

(defn job-req
  ([body tags]
   {:tags tags
    :request_body body})
  ([body tags dependencies]
   (assoc (job-req body tags) :dependencies dependencies)))

(def success             server-protocol/success)
(def error               server-protocol/error)
(def cancelled           server-protocol/cancelled)
(def add-dependencies    server-protocol/add-dependencies)
(def try-later           server-protocol/try-later)

(defprotocol Client
  (put-job! [this job-id job-req])
  (get-job [this job-id])
  (pause-job! [this job-id])

  (request-work! [this tags])
  (heartbeat! [this commitment-id])
  (complete-work! [this commitment-id result])

  (do-work! [this tags f]))

