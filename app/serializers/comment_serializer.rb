class CommentSerializer < ActiveModel::Serializer
  attributes :id, :body,  :post_id
  has_one :user
end
