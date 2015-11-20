class ApplicationController < ActionController::API

	include ActionController::HttpAuthentication::Basic::ControllerMethods
	include ActionController::HttpAuthentication::Token::ControllerMethods

	before_action :authenticate_user_with_token, except: [:login]

	def login
		authenticate_with_http_basic do |email, password|
			user = User.find_by(email: email)
			if user && user.password == password
				render json: { token: user.auth_token, :user => user.as_json(:only => [:id, :name, :email]) }
			else
				render json: {error: 'incorrect credentials'}, status: 401
			end
		end
	end

	def loginVerification
		render json: { login: 'success', :user => @current_user.as_json(:only => [:id, :name, :email])}, status: 200
	end

	private


	def authenticate_user_with_token
		if user = authenticate_with_http_token { |token, options| User.find_by(auth_token: token) }
			@current_user = user
		else
			render json: { error: 'Bad Token'}, status: 401
		end
	end
end
