class ImageSerializer < ActiveModel::Serializer
  attributes :id, :aws_bucket, :aws_key
end
