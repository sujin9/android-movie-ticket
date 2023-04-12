package woowacourse.movie

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.domain.Movie
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movies = setMovies()
        setMovieList(movies)
    }

    private fun setMovies(): List<Movie> {
        val movie = Movie(
            R.drawable.parasite,
            "기생충",
            LocalDate.of(2023, 9, 12),
            LocalDate.of(2023, 9, 30),
            131,
            "직업도 없이 허름한 반지하에 사는 기택 가족에게 돈을 벌 기회가 찾아온다. 친구의 소개로 부잣집 딸 다혜의 과외 선생님을 맡게 된 기택의 아들, 기우는 기대감에 부푼 채 글로벌 IT기업을 이끄는 박 사장의 저택에 들어간다."
        )

        return listOf(movie)
    }

    private fun setMovieList(movies: List<Movie>) {
        val moviesView = findViewById<ListView>(R.id.main_movie_list)
        moviesView.adapter = MovieListAdapter(
            movies,
            object : MovieListAdapter.ItemClickListener {
                override fun onItemClick(position: Int) {
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
        intent.putExtra("movie", movie)
        startActivity(intent)
    }
}
