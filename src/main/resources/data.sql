-- Insert data into roles table
INSERT INTO roles (id, name, description, created_at) VALUES
  (gen_random_uuid(), 'admin', 'Administrator with full access', CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'editor', 'Can edit and manage posts', CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'user', 'Regular user with limited access', CURRENT_TIMESTAMP);

-- Insert data into users table
INSERT INTO users (id, username, email, password_hash, avatar_url, created_at, updated_at) VALUES
  (gen_random_uuid(), 'john_doe', 'john.doe@example.com', 'hashed_password_1', 'https://example.com/avatars/john.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'admin_user', 'admin@example.com', 'hashed_password_2', 'https://example.com/avatars/admin.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'jane_doe', 'jane.doe@example.com', 'hashed_password_3', 'https://example.com/avatars/jane.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into categories table
INSERT INTO categories (id, name, created_at) VALUES
  (gen_random_uuid(), 'Technology', CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'Health', CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'Travel', CURRENT_TIMESTAMP);

-- Insert data into posts table
INSERT INTO posts (id, title, content, author_id, category_id, created_at, updated_at) VALUES
  (gen_random_uuid(), 'The Future of AI', 'Content about AI...', (SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM categories WHERE name = 'Technology'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), '10 Healthy Living Tips', 'Content about health...', (SELECT id FROM users WHERE username = 'jane_doe'), (SELECT id FROM categories WHERE name = 'Health'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'Top 5 Travel Destinations', 'Content about travel...', (SELECT id FROM users WHERE username = 'admin_user'), (SELECT id FROM categories WHERE name = 'Travel'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into tags table
INSERT INTO tags (id, name) VALUES
  (gen_random_uuid(), 'AI'),
  (gen_random_uuid(), 'Health'),
  (gen_random_uuid(), 'Travel');

-- Insert data into post_tags table
INSERT INTO post_tags (post_id, tag_id) VALUES
  ((SELECT id FROM posts WHERE title = 'The Future of AI'), (SELECT id FROM tags WHERE name = 'AI')),
  ((SELECT id FROM posts WHERE title = '10 Healthy Living Tips'), (SELECT id FROM tags WHERE name = 'Health')),
  ((SELECT id FROM posts WHERE title = 'Top 5 Travel Destinations'), (SELECT id FROM tags WHERE name = 'Travel'));

-- Insert data into comments table
INSERT INTO comments (id, post_id, author_id, parent_comment_id, content, created_at, updated_at) VALUES
  -- Comments for 'The Future of AI'
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = 'The Future of AI'), (SELECT id FROM users WHERE username = 'jane_doe'), NULL, 'Great insights about AI!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = 'The Future of AI'), (SELECT id FROM users WHERE username = 'admin_user'), NULL, 'Thank you for sharing!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  -- Replies to the first comment
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = 'The Future of AI'), (SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM comments WHERE content = 'Great insights about AI!'), 'I agree, very informative!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

  -- Comments for '10 Healthy Living Tips'
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = '10 Healthy Living Tips'), (SELECT id FROM users WHERE username = 'john_doe'), NULL, 'I found this very helpful.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  -- Reply to the first comment
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = '10 Healthy Living Tips'), (SELECT id FROM users WHERE username = 'jane_doe'), (SELECT id FROM comments WHERE content = 'I found this very helpful.'), 'Glad it helped!', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into post_views table
INSERT INTO post_views (id, post_id, user_id, ip_address, user_agent, created_at) VALUES
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = 'The Future of AI'), (SELECT id FROM users WHERE username = 'john_doe'), '192.168.0.1', 'Mozilla/5.0', CURRENT_TIMESTAMP),
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = '10 Healthy Living Tips'), (SELECT id FROM users WHERE username = 'jane_doe'), '192.168.0.2', 'Mozilla/5.0', CURRENT_TIMESTAMP),
  (gen_random_uuid(), (SELECT id FROM posts WHERE title = 'Top 5 Travel Destinations'), NULL, '192.168.0.3', 'Mozilla/5.0', CURRENT_TIMESTAMP);

-- Insert data into user_roles table
INSERT INTO user_roles (user_id, role_id) VALUES
  ((SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM roles WHERE name = 'user')),
  ((SELECT id FROM users WHERE username = 'admin_user'), (SELECT id FROM roles WHERE name = 'admin')),
  ((SELECT id FROM users WHERE username = 'jane_doe'), (SELECT id FROM roles WHERE name = 'editor'));
