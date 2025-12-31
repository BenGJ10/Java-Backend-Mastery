<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Jobs – Job Description Portal</title>
    
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

        /* Page header */
        .page-header {
            padding: 4rem 0 2.5rem;
            text-align: center;
            background: linear-gradient(135deg, #f0fdfa 0%, #ecfeff 100%);
        }

        .page-title {
            font-size: clamp(2.2rem, 5vw, 3.5rem);
            font-weight: 800;
            color: var(--dark);
        }

        /* Job cards */
        .job-card {
            border: none;
            border-radius: 1.25rem;
            overflow: hidden;
            box-shadow: 0 8px 24px rgba(0,0,0,0.07);
            transition: all 0.28s cubic-bezier(0.34, 1.56, 0.64, 1);
            background: white;
            height: 100%;
        }

        .job-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.12);
        }

        .job-title {
            font-size: 1.4rem;
            font-weight: 700;
            color: var(--primary-dark);
            margin-bottom: 1rem;
        }

        .job-meta {
            font-size: 0.95rem;
            color: var(--gray);
            margin-bottom: 0.75rem;
        }

        .tech-badge {
            background: rgba(13, 148, 136, 0.08);
            color: var(--primary-dark);
            border: 1px solid rgba(13, 148, 136, 0.2);
            font-weight: 500;
            padding: 0.4rem 0.9rem;
            border-radius: 2rem;
            font-size: 0.88rem;
            margin: 0 0.35rem 0.5rem 0;
            display: inline-block;
        }

        .no-jobs {
            background: white;
            border-radius: 1.25rem;
            padding: 4rem 2rem;
            box-shadow: 0 8px 24px rgba(0,0,0,0.06);
            text-align: center;
        }

        /* Footer (same as home) */
        footer {
            background: white;
            border-top: 1px solid var(--light-gray);
            padding: 4rem 0 2rem;
            color: var(--gray);
            margin-top: 5rem;
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
                <li class="nav-item"><a class="nav-link active" href="viewalljobs">All Jobs</a></li>
                <li class="nav-item"><a class="nav-link" href="addjob">Post Job</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Contact</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Header -->
<section class="page-header">
    <div class="container">
        <h1 class="page-title">All Job Opportunities</h1>
        <p class="lead text-muted mt-3">Discover positions across technologies and experience levels</p>
    </div>
</section>

<!-- Jobs list -->
<section class="pb-5">
    <div class="container">
        <c:choose>
            <c:when test="${empty jobPosts}">
                <div class="no-jobs mx-auto" style="max-width: 700px;">
                    <i class="fas fa-briefcase fs-1 text-muted mb-4 d-block"></i>
                    <h4 class="mb-3">No jobs posted yet</h4>
                    <p class="text-muted mb-4">Be the first to add an exciting opportunity!</p>
                    <a href="addjob" class="btn btn-primary btn-lg" style="background: var(--primary); border: none;">
                        Post a Job Now
                    </a>
                </div>
            </c:when>
            
            <c:otherwise>
                <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                    <c:forEach var="jobPost" items="${jobPosts}">
                        <div class="col">
                            <div class="job-card p-4">
                                <h3 class="job-title">${jobPost.postProfile}</h3>
                                
                                <div class="mb-3">
                                    <div class="job-meta">
                                        <i class="far fa-clock me-2"></i>
                                        Experience: <strong>${jobPost.reqExperience} year${jobPost.reqExperience != 1 ? 's' : ''}</strong>
                                    </div>
                                </div>

                                <p class="mb-4 text-secondary">
                                    ${jobPost.postDesc}
                                </p>

                                <div class="mt-4">
                                    <strong class="d-block mb-2 text-dark">Tech Stack:</strong>
                                    <div class="d-flex flex-wrap">
                                        <c:forEach var="tech" items="${jobPost.postTechStack}">
                                            <span class="tech-badge">${tech}</span>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
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
    // Navbar scroll shadow
    window.addEventListener('scroll', () => {
        document.querySelector('.navbar').classList.toggle('scrolled', window.scrollY > 20);
    });
</script>
</body>
</html>