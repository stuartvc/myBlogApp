class UserSerializer < ActiveModel::Serializer
  attributes :id, :email

  has_many :posts, serializer: PostPreviewSerializer
end
