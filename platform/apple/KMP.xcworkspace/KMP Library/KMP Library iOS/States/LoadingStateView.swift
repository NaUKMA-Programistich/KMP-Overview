import SwiftUI

struct LoadingStateView: View {
    var body: some View {
        ZStack {
            Color.clear
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle())
        }
    }
}
