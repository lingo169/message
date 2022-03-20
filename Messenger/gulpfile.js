"use strict";

const gulp         = require('gulp');
const sass         = require('gulp-sass')(require('sass'));
const fiber        = require('fibers');
const autoprefixer = require('gulp-autoprefixer');
const sourcemaps   = require('gulp-sourcemaps');
const cleancss     = require('gulp-clean-css');
const rename       = require('gulp-rename');
const rimraf       = require('rimraf');
const fileinclude  = require('gulp-file-include');
const webpack      = require('webpack-stream');
const named        = require('vinyl-named');
const browsersync  = require('browser-sync').create();
const rtlcss       = require('gulp-rtlcss');

const paths = {

    dist: {
        base: {
            dir: './dist'
        },

        js: {
            dir: './dist/assets/js'
        },

        scss: {
            dir: './dist/assets/css'
        },

        assets: {
            dir: './dist/assets'
        },
        img: {
            dir:    './dist/assets/img'
        }
    },
    src: {
        base: {
            dir:   './src/',
            files: './src/**/*.*'
        },
        html: {
            files: './src/**/*.html'
        },
        assets: {
            dir:    './src/assets',
            files:  '.src/assets/**/*'
        },
        partials: {
            dir:   './src/partials',
            files: './src/partials/**/*'
        },
        scss: {
            dir:    './src/assets/scss',
            files:  './src/assets/scss/**/*',
            main:   './src/assets/scss/*.scss'
        },
        js: {
            dir:    './src/assets/js',
            files:  './src/assets/js/**/*',
            main:   './src/assets/js/template.js',
        },
        img: {
            dir:    './src/assets/img',
            files:  './src/assets/img/**/*.*'
        },
        libraries: {
            dir:    './src/assets/libraries',
            files:  './src/assets/libraries/**/**'
        },
        tmp: {
            dir:    './src/.tmp',
            assets: './src/.tmp/assets',
            js:     './src/.tmp/assets/js',
            scss:   './src/.tmp/assets/css',
            img:    './src/.tmp/assets/img'
        }
    },

};

gulp.task('browsersync', function(callback) {
    browsersync.init({
        server: {
            baseDir: paths.src.tmp.dir,
        },
    });
    callback();
});

gulp.task('browsersyncReload', function(callback) {
    browsersync.reload();
    callback();
});

//
// Html
//

gulp.task('html', function(callback) {
    return gulp
      .src([paths.src.html.files])
        .pipe(fileinclude({
            prefix:   '@@',
            basepath: '@file',
            indent:   true
        }))
        .pipe(gulp.dest(paths.src.tmp.dir));
});

gulp.task('html:dist', function(callback) {
    return gulp
      .src([paths.src.html.files])
        .pipe(fileinclude({
            prefix:   '@@',
            basepath: '@file',
            indent:   true
        }))
        .pipe(gulp.dest(paths.dist.base.dir))
});

//
// Javascript
//

gulp.task('javascript', function(callback) {
    return gulp
        .src([
            paths.src.js.main,
        ])
        .pipe(named())
        .pipe(webpack({
            mode:    'production',
            devtool: 'source-map',
            optimization: {
                splitChunks: {
                  cacheGroups: {
                    vendor: {
                      test:   /[\\/](node_modules)[\\/].+\.js$/,
                      name:   'vendor',
                      chunks: 'all'
                    }
                  }
                },
            }
        }))
        .pipe(gulp.dest(paths.src.tmp.js));
    done();
});

gulp.task('javascript:dist', function(callback) {
    return gulp
        .src([
            paths.src.js.main,
        ])
      .pipe(named())
      .pipe(webpack({
        optimization: {
            splitChunks: {
              cacheGroups: {
                vendor: {
                  test:   /[\\/](node_modules)[\\/].+\.js$/,
                  name:   'vendor',
                  chunks: 'all'
                }
              }
            },
        }
    }))
      .pipe(gulp.dest(paths.dist.js.dir));
    done();
});

//
// Styles
//

gulp.task('scss', function() {
    return gulp
        .src(paths.src.scss.main)
            .pipe(sourcemaps.init())
            .pipe(sass({fiber: fiber})
                .on('error', sass.logError))
            .pipe(autoprefixer({
                overrideBrowserslist: ['last 2 versions']
            }))
            .pipe(rename({suffix: '.bundle'}))
            .pipe(sourcemaps.write('.'))
            .pipe(gulp.dest(paths.src.tmp.scss))
            .pipe(browsersync.stream());
});

gulp.task('scss:dist', function() {
    return gulp
        .src(paths.src.scss.main)
            .pipe(sass({fiber: fiber})
                .on('error', sass.logError))
            .pipe(autoprefixer({
                overrideBrowserslist: ['last 2 versions']
            }))
            .pipe(cleancss({level: {1: {specialComments: 0}}}))
            .pipe(rename({suffix: '.bundle'}))
            .pipe(gulp.dest(paths.dist.scss.dir))
            .pipe(browsersync.stream());
});

//
// Copy assets(fonts, images and etc)
//

gulp.task('copy:assets', function() {
    return gulp
        .src([
            paths.src.base.files,
            '!' + paths.src.html.files,
            '!' + paths.src.partials.dir,
            '!' + paths.src.partials.files,
            '!' + paths.src.scss.dir,
            '!' + paths.src.scss.files,
            '!' + paths.src.js.dir,
            '!' + paths.src.js.files,
            '!' + paths.src.libraries.dir,
            '!' + paths.src.libraries.files,
        ])
        .pipe(gulp.dest(paths.src.tmp.dir));
});

gulp.task('copy:assets:dist', function() {
    return gulp
        .src([
            paths.src.base.files,
            '!' + paths.src.html.files,
            '!' + paths.src.partials.dir,
            '!' + paths.src.partials.files,
            '!' + paths.src.scss.dir,
            '!' + paths.src.scss.files,
            '!' + paths.src.js.dir,
            '!' + paths.src.js.files,
            '!' + paths.src.libraries.dir,
            '!' + paths.src.libraries.files,
        ])
        .pipe(gulp.dest(paths.dist.base.dir));
});

//
// Clean ".tmp" folder
//

gulp.task('clean:tmp', function (cb) {
    rimraf(paths.src.tmp.dir, cb);
});

//
// Clean "dist" folder
//

gulp.task('clean:dist', function (cb) {
    rimraf(paths.dist.base.dir, cb);
});

//
// Watch
//

gulp.task('watch', function() {
    gulp.watch([paths.src.scss.files], gulp.series('scss'));
    gulp.watch([paths.src.js.files], gulp.series('javascript', 'browsersyncReload'));
    gulp.watch([paths.src.img.files], gulp.series('copy:assets', 'browsersyncReload'));
    gulp.watch([paths.src.html.files, paths.src.partials.files], gulp.series('html', 'browsersyncReload'));
});

//
// Tasks
//

gulp.task('build',   gulp.series(gulp.parallel('clean:dist'), gulp.parallel('scss:dist', 'html:dist', 'javascript:dist', 'copy:assets:dist')));
gulp.task('default', gulp.series(gulp.parallel('clean:tmp'), gulp.parallel('html', 'scss', 'javascript', 'copy:assets'), gulp.parallel('browsersync', 'watch')));