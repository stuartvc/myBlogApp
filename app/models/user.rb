class User < ActiveRecord::Base
	before_create -> { self.auth_token = SecureRandom.hex }
	has_many :posts
	has_many :comments

	def image
		Image.find_by_id(image_id)
	end
end
