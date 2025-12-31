<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Post a Job – Job Description Portal</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    
    <style>
        :root {
            --primary:    #0d9488;
            --primary-dark: #0f766e;
            --primary-light: #5eead4;
            --dark:       #0f172a;
            --gray:       #64748b;
            --light:      #f8fafc;
            --light-gray: #e2e8f0;
        }

        body {
            background-color: var(--light);
            color: var(--dark);
            font-family: system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
        }

        .navbar {
            position: sticky;
            top: 0;
            z-index: 1000;
            background-color: white;
            border-bottom: 1px solid var(--light-gray);
            transition: box-shadow 0.3s ease;
        }

        .navbar.scrolled {
            box-shadow: 0 4px 16px rgba(0,0,0,0.08);
        }

        .navbar-brand {
            font-weight: 800;
            font-size: 1.5rem;
            color: var(--primary-dark) !important;
        }

        .nav-link {
            font-weight: 500;
            color: var(--gray) !important;
            transition: color 0.2s;
        }

        .nav-link:hover,
        .nav-link.active {
            color: var(--primary) !important;
        }

        .form-wrapper {
            max-width: 780px;
            margin: 0 auto;
            padding: 5rem 1rem 6rem;
        }

        .form-card {
            border: none;
            border-radius: 1.5rem;
            overflow: hidden;
            box-shadow: 0 12px 32px rgba(0,0,0,0.09);
            background: white;
        }

        .form-header {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            color: white;
            padding: 3.5rem 2.5rem 2.5rem;
            text-align: center;
        }

        .form-header h2 {
            font-weight: 800;
            font-size: 2.1rem;
            margin: 0;
            letter-spacing: -0.5px;
        }

        .form-body {
            padding: 2.5rem 2.5rem 3rem;
        }

        .form-label {
            font-weight: 600;
            color: var(--dark);
            margin-bottom: 0.5rem;
            font-size: 1rem;
        }

        .form-control, .form-select {
            border-radius: 0.85rem;
            padding: 0.75rem 1.1rem;
            border: 1px solid var(--light-gray);
            font-size: 1rem;
            transition: all 0.2s;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary-light);
            box-shadow: 0 0 0 0.3rem rgba(13,148,136,0.2);
            outline: none;
        }

        .form-select[multiple] {
            height: 220px !important;
            padding: 0.6rem 1rem;
        }

        .helper-text {
            font-size: 0.875rem;
            color: var(--gray);
            margin-top: 0.4rem;
        }

        .btn-submit {
            background: var(--primary);
            border: none;
            font-weight: 700;
            font-size: 1.15rem;
            padding: 0.9rem 2.5rem;
            border-radius: 3rem;
            transition: all 0.25s;
            width: 100%;
            max-width: 320px;
            margin: 2rem auto 0;
            display: block;
        }

        .btn-submit:hover {
            background: var(--primary-dark);
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(13,148,136,0.25);
        }

        /* Footer */
        footer {
            background: white;
            border-top: 1px solid var(--light-gray);
            padding: 4rem 0 2rem;
            color: var(--gray);
            margin-top: 4rem;
        }

        footer h5, footer h6 { color: var(--dark); font-weight: 700; }

        .social-icon {
            font-size: 1.6rem;
            color: var(--gray);
            transition: all 0.2s;
        }

        .social-icon:hover {
            color: var(--primary);
            transform: translateY(-3px);
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg">
    <div class="container">
        <a class="navbar-brand" href="home">Job Description Portal</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto align-items-center">
                <li class="nav-item"><a class="nav-link" href="home">Home</a></li>
                <li class="nav-item"><a class="nav-link active" href="addjob">Post Job</a></li>
                <li class="nav-item"><a class="nav-link" href="viewalljobs">All Jobs</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Contact</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Form Section -->
<section class="form-wrapper">
    <div class="form-card">
        <div class="form-header">
            <h2>Post a New Job Opening</h2>
        </div>
        
        <div class="form-body">
            <form action="handleForm" method="post">
                <div class="mb-4">
                    <label for="postId" class="form-label">Post ID</label>
                    <input type="text" class="form-control" id="postId" name="postId" required placeholder="e.g. JOB-2025-001">
                </div>

                <div class="mb-4">
                    <label for="postProfile" class="form-label">Job Title / Profile</label>
                    <input type="text" class="form-control" id="postProfile" name="postProfile" required 
                           placeholder="e.g. Senior Backend Engineer – Java">
                </div>

                <div class="mb-4">
                    <label for="postDesc" class="form-label">Job Description</label>
                    <textarea class="form-control" id="postDesc" name="postDesc" rows="5" required 
                              placeholder="Describe responsibilities, requirements, what makes this role exciting..."></textarea>
                </div>

                <div class="mb-4">
                    <label for="reqExperience" class="form-label">Required Experience (years)</label>
                    <input type="number" class="form-control" id="reqExperience" name="reqExperience" min="0" required 
                           placeholder="e.g. 3">
                </div>

                <div class="mb-4">
                    <label for="postTechStack" class="form-label">Tech Stack / Skills (select multiple)</label>
                    <select multiple class="form-select" id="postTechStack" name="postTechStack" required>
                        <option value="Java">Java</option>
                        <option value="Spring Boot">Spring Boot</option>
                        <option value="JavaScript">JavaScript</option>
                        <option value="TypeScript">TypeScript</option>
                        <option value="React">React</option>
                        <option value="Angular">Angular</option>
                        <option value="Vue.js">Vue.js</option>
                        <option value="Node.js">Node.js</option>
                        <option value="Express.js">Express.js</option>
                        <option value="Python">Python</option>
                        <option value="Django">Django</option>
                        <option value="Flask">Flask</option>
                        <option value="Go">Go</option>
                        <option value="Rust">Rust</option>
                        <option value="Kotlin">Kotlin</option>
                        <option value="Swift">Swift</option>
                        <option value="Flutter">Flutter</option>
                        <option value="React Native">React Native</option>
                        <option value="AWS (Amazon Web Services)">AWS (Amazon Web Services)</option>
                        <option value="Azure">Azure</option>
                        <option value="Google Cloud">Google Cloud</option>
                        <option value="Docker">Docker</option>
                        <option value="Kubernetes">Kubernetes</option>
                        <option value="Jenkins">Jenkins</option>
                        <option value="DevOps">DevOps</option>
                        <option value="Machine Learning">Machine Learning</option>
                        <option value="Artificial Intelligence">Artificial Intelligence</option>
                        <option value="TensorFlow">TensorFlow</option>
                        <option value="PyTorch">PyTorch</option>
                        <option value="Blockchain">Blockchain</option>
                        <option value="Cybersecurity">Cybersecurity</option>
                        <!-- Keep or trim the rest as needed – this is already a good selection -->
                    </select>
                    <div class="helper-text">Hold Ctrl (Windows) or Cmd (Mac) to select multiple technologies</div>
                </div>

                <button type="submit" class="btn btn-submit">Publish Job Post</button>
            </form>
        </div>
    </div>
</section>

<!-- Footer -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-md-4 mb-5 mb-md-0">
                <h5>Job Description Portal</h5>
                <p class="mt-3">Connecting great people with great opportunities — every day</p>
            </div>

            <div class="col-md-4 mb-5 mb-md-0">
                <h6>Quick Links</h6>
                <ul class="list-unstyled mt-3">
                    <li class="mb-2"><a href="home" class="text-reset text-decoration-none">Home</a></li>
                    <li class="mb-2"><a href="viewalljobs" class="text-reset text-decoration-none">All Jobs</a></li>
                    <li class="mb-2"><a href="addjob" class="text-reset text-decoration-none">Post a Job</a></li>
                    <li><a href="#" class="text-reset text-decoration-none">Contact</a></li>
                </ul>
            </div>

            <div class="col-md-4">
                <h6>Connect</h6>
                <div class="d-flex gap-4 mt-3">
                    <a href="#" class="social-icon"><i class="fab fa-linkedin-in"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="social-icon"><i class="fab fa-github"></i></a>
                </div>
                <p class="mt-4 small">Made by Ben Gregory John</p>
            </div>
        </div>

        <div class="text-center mt-5 pt-4 border-top">
            <small>© Job Description Portal — All rights reserved</small>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Navbar scroll effect
    window.addEventListener('scroll', () => {
        document.querySelector('.navbar').classList.toggle('scrolled', window.scrollY > 20);
    });
</script>
</body>
</html>