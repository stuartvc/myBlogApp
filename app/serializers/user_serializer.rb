class UserSerializer < ActiveModel::Serializer
  attributes :id, :name, :email, :password
  has_one :image
end
