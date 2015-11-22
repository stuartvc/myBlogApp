class PostsController < ApplicationController
  before_action :set_post, only: [:show, :update, :destroy]

  # GET /posts
  # GET /posts.json
  def index
    posts = Post.last(5).reverse
    
    render json: posts, each_serializer: PostPreviewSerializer
  end

  # GET /posts/1
  # GET /posts/1.json
  def show
    render json: @post, serializer: PostSerializer
  end

  # POST /posts
  # POST /posts.json
  def create
    @post = Post.new(post_params.merge(:user_id => @current_user.id))

    if @post.save
      render json: @post, status: :created, location: @post
    else
      render json: @post.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /posts/1
  # PATCH/PUT /posts/1.json
  def update
    @post = Post.find(params[:id])

    if @post.update(post_params)
      head :no_content
    else
      render json: @post.errors, status: :unprocessable_entity
    end
  end

  # DELETE /posts/1
  # DELETE /posts/1.json
  def destroy
    if @current_user.id == @post.user_id
      @post.destroy

      head :no_content
    else
      render json: { error: 'Not Authorised'}, status: 401
    end
    
  end

  # GET /users/:user_id/posts
  # GET /users/:user_id/posts.json
  def users_posts
    @posts = @current_user.posts

    render json: @posts, each_serializer: PostPreviewSerializer
  end

  private

    def set_post
      @post = Post.find(params[:id])
    end

    def post_params
      params.require(:post).permit(:title, :body)
    end
end
