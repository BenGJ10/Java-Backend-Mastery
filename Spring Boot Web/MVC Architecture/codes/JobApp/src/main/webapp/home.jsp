<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Description Portal</title>
    
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

        * { box-sizing: border-box; }

        body {
            background-color: var(--light);
            color: var(--dark);
            font-family: system-ui, -apple-system, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
        }

        /* Sticky navbar with scroll effect */
        .navbar {
            position: sticky;
            top: 0;
            z-index: 1000;
            background-color: white;
            border-bottom: 1px solid var(--light-gray);
            transition: box-shadow 0.3s ease, background-color 0.3s ease;
        }

        .navbar.scrolled {
            box-shadow: 0 4px 16px rgba(0,0,0,0.08);
        }

        .navbar-brand {
            font-weight: 800;
            font-size: 1.5rem;
            color: var(--primary-dark) !important;
            letter-spacing: -0.5px;
        }

        .nav-link {
            font-weight: 500;
            color: var(--gray) !important;
            padding: 0.6rem 1rem !important;
            transition: color 0.2s ease;
        }

        .nav-link:hover,
        .nav-link.active {
            color: var(--primary) !important;
        }

        /* Hero */
        .hero-section {
            padding: 7rem 0 6rem;
            text-align: center;
            background: linear-gradient(135deg, #f0fdfa 0%, #ecfeff 100%);
            position: relative;
            overflow: hidden;
        }

        .hero-section::before {
            content: '';
            position: absolute;
            inset: 0;
            background: radial-gradient(circle at 20% 30%, rgba(94,234,212,0.18) 0%, transparent 60%);
            pointer-events: none;
        }

        .hero-title {
            font-size: clamp(2.5rem, 6vw, 4.2rem);
            font-weight: 800;
            line-height: 1.1;
            margin-bottom: 1.25rem;
            color: var(--dark);
        }

        .hero-lead {
            font-size: 1.25rem;
            max-width: 640px;
            margin: 0 auto 2.5rem;
            color: var(--gray);
        }

        /* Search bar */
        .search-container {
            max-width: 720px;
            margin: 0 auto;
        }

        .search-input {
            height: 3.6rem;
            border-radius: 3rem !important;
            padding-left: 1.8rem !important;
            padding-right: 5.5rem !important;
            border: 2px solid transparent;
            box-shadow: 0 4px 12px rgba(0,0,0,0.06);
            transition: all 0.25s ease;
        }

        .search-input:focus {
            border-color: var(--primary-light);
            box-shadow: 0 0 0 0.4rem rgba(13,148,136,0.18);
            outline: none;
        }

        .search-btn {
            position: absolute;
            right: 0.5rem;
            top: 50%;
            transform: translateY(-50%);
            width: 2.8rem;
            height: 2.8rem;
            border-radius: 50% !important;
            background: var(--primary) !important;
            border: none !important;
            transition: all 0.2s;
        }

        .search-btn:hover {
            background: var(--primary-dark) !important;
            transform: translateY(-50%) scale(1.08);
        }

        /* Skills tags */
        .skills-container {
            margin-top: 3rem;
        }

        .skill-tag {
            background: white;
            border: 1px solid var(--light-gray);
            color: var(--gray);
            padding: 0.55rem 1.1rem;
            border-radius: 2rem;
            font-size: 0.95rem;
            font-weight: 500;
            transition: all 0.2s;
        }

        .skill-tag:hover {
            background: var(--primary-light);
            color: var(--primary-dark);
            border-color: var(--primary-light);
            transform: translateY(-1px);
        }

        /* Cards */
        .feature-card {
            border: none;
            border-radius: 1.25rem;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0,0,0,0.07);
            transition: all 0.28s cubic-bezier(0.34, 1.56, 0.64, 1);
            background: white;
            height: 100%;
        }

        .feature-card:hover {
            transform: translateY(-12px);
            box-shadow: 0 22px 40px rgba(0,0,0,0.12);
        }

        .card-icon {
            font-size: 3.8rem;
            color: var(--primary);
            margin-bottom: 1.6rem;
        }

        .btn-primary-custom {
            background: var(--primary);
            border: none;
            font-weight: 600;
            padding: 0.8rem 2rem;
            border-radius: 3rem;
            transition: all 0.25s;
        }

        .btn-primary-custom:hover {
            background: var(--primary-dark);
            transform: translateY(-2px);
        }

        /* Footer */
        footer {
            background: white;
            border-top: 1px solid var(--light-gray);
            padding: 4rem 0 2rem;
            color: var(--gray);
        }

        footer h5, footer h6 {
            color: var(--dark);
            font-weight: 700;
        }

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
                <li class="nav-item"><a class="nav-link active" href="home">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="viewalljobs">All Jobs</a></li>
                <li class="nav-item"><a class="nav-link" href="addjob">Post Job</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Contact</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Hero -->
<section class="hero-section">
    <div class="container">
        <h1 class="hero-title">Discover Your Next Great Opportunity</h1>
        <p class="hero-lead">Thousands of exciting positions from innovative companies — updated daily</p>

        <!-- Search -->
        <div class="search-container position-relative">
            <form action="viewalljobs" method="get">
                <input type="search" name="q" class="form-control form-control-lg search-input shadow-sm"
                       placeholder="Search by job title, skill, company, location..." autocomplete="off">
                <button type="submit" class="btn btn-primary search-btn">
                    <i class="fas fa-search"></i>
                </button>
            </form>
        </div>

        <!-- Popular skills -->
        <div class="skills-container text-center">
            <p class="text-muted mb-3 fw-medium">Popular today</p>
            <div class="d-flex flex-wrap justify-content-center gap-2 gap-md-3">
                <span class="skill-tag">Java</span>
                <span class="skill-tag">Spring Boot</span>
                <span class="skill-tag">React</span>
                <span class="skill-tag">DevOps</span>
                <span class="skill-tag">AWS</span>
                <span class="skill-tag">Python</span>
                <span class="skill-tag">Go</span>
                <span class="skill-tag">Kubernetes</span>
            </div>
        </div>
    </div>
</section>

<!-- Action cards -->
<section class="py-5 py-lg-6 bg-white">
    <div class="container">
        <div class="row g-4 justify-content-center">
            <div class="col-lg-5">
                <div class="feature-card text-center p-5">
                    <i class="fas fa-briefcase card-icon"></i>
                    <h4 class="mb-3 fw-bold">Browse All Jobs</h4>
                    <p class="text-muted mb-4">Explore hundreds of open positions across every major tech stack</p>
                    <a href="viewalljobs" class="btn btn-primary-custom btn-lg">View All Jobs</a>
                </div>
            </div>

            <div class="col-lg-5">
                <div class="feature-card text-center p-5">
                    <i class="fas fa-plus-circle card-icon"></i>
                    <h4 class="mb-3 fw-bold">Post a New Position</h4>
                    <p class="text-muted mb-4">Reach thousands of skilled candidates in minutes</p>
                    <a href="addjob" class="btn btn-primary-custom btn-lg">Add New Job</a>
                </div>
            </div>
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