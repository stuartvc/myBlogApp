class PostSerializer < ActiveModel::Serializer
  attributes :id, :title, :body
  has_one :user
  has_many :comments
end
