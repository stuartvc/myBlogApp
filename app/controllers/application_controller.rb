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

	def awsVerification
		s3Client = Aws::CognitoIdentity::Client.new
		resp = s3Client.get_open_id_token_for_developer_identity({
			identity_pool_id: "us-east-1:31d8dd8e-2283-4476-9eae-3dfdc271af50", 
			logins: {
    			"login.stuartvancampen.myblog" => @current_user.id.to_s + ":" + @current_user.email
  			}
  		})
  		render json: { identity_id: resp.identity_id, token: resp.token}

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
