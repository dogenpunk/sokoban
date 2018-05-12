(ns sokoban.views
  (:require [re-frame.core :as rf]
            [sokoban.events :as events]
            [sokoban.subs :as subs]))

(def cell-size 40)

(defn cell [cell-type]
  [:span.button {:style {:width cell-size
                         :height cell-size}}
   cell-type])

(defn board []
  (let [level (rf/subscribe [::subs/level])]
    [:div
     (doall (for [y (range (count @level))]
              ^{:key y}
              [:div (doall (for [x (range (count (get @level y)))]
                             ^{:key x} [cell (get-in @level [y x])]))]))]))

(defn game []
  [:div
   [:div [:label (str "Remaining: " @(rf/subscribe [::subs/remaining-count]))]]
   [board]
   [:hr]
   [:div
    [:label (str "Current move: " @(rf/subscribe [::subs/current-move]) " ")]
    [:span.icon.button
     {:on-click #(rf/dispatch [::events/set-current-move 0])}
     [:i.fas.fa-fast-backward {:aria-hidden true}]]
    [:span.icon.button
     {:on-click #(rf/dispatch [::events/update-current-move dec])}
     [:i.fas.fa-step-backward {:aria-hidden true}]]
    [:span.icon.button
     {:on-click #(rf/dispatch [::events/update-current-move inc])}
     [:i.fas.fa-step-forward {:aria-hidden true}]]
    [:span.icon.button
     {:on-click #(rf/dispatch [::events/set-current-move
                               (dec @(rf/subscribe [::subs/history-size]))])}
     [:i.fas.fa-fast-forward {:aria-hidden true}]]]])

(defn main-panel []
  [game])
