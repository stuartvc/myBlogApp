class ApplicationController < ActionController::API

	include ActionController::HttpAuthentication::Basic::ControllerMethods
	include ActionController::HttpAuthentication::Token::ControllerMethods

	before_action :authenticate_user_with_token, except: [:login]

	def login
		authenticate_with_http_basic do |email, password|
			user = User.find_by(email: email)
			if user && user.password == password
				render json: { token: user.auth_token }
			else
				render json: {error: 'incorrect credentials'}, status: 401
			end
		end
	end

	private


	def authenticate_user_with_token
	  unless authenticate_with_http_token { |token, options| User.find_by(auth_token: token) }
	    render json: { error: 'Bad Token'}, status: 401
	  end
	end
end
