package woowacourse.movie.ui.movielist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.CustomListView
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.ui.const.KEY_MOVIE
import woowacourse.movie.ui.moviedetail.MovieDetailActivity
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movies = getMovies()
        setMovieList(movies)
    }

    private fun getMovies(): List<Movie> {
        return List(10) {
            Movie(
                R.drawable.parasite,
                "기생충",
                LocalDate.of(2023, 9, 12),
                LocalDate.of(2023, 9, 30),
                131,
                "직업도 없이 허름한 반지하에 사는 기택 가족에게 돈을 벌 기회가 찾아온다. 친구의 소개로 부잣집 딸 다혜의 과외 선생님을 맡게 된 기택의 아들, 기우는 기대감에 부푼 채 글로벌 IT기업을 이끄는 박 사장의 저택에 들어간다."
            )
        }
    }

    private fun setMovieList(movies: List<Movie>) {
        val moviesView = findViewById<CustomListView>(R.id.main_movie_list)
        moviesView.adapter = MovieListAdapter(
            movies,
            object : MovieListAdapter.ItemButtonClickListener {
                override fun onClick(position: Int) {
                    moveToDetailActivity(movies[position])
                }
            }
        )
        moviesView.setOnItemClickListener { parent, view, position, id ->
            moveToDetailActivity(movies[position])
        }
    }

    private fun moveToDetailActivity(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(KEY_MOVIE, movie)
        startActivity(intent)
    }
}
