import SwiftUI

struct TodoDialogView<Content: View>: View {
    let title: String
    let content: Content

    init(title: String, @ViewBuilder content: () -> Content) {
        self.title = title
        self.content = content()
    }

    var body: some View {
        NavigationView {
            content
                .navigationTitle(title)
                .navigationBarTitleDisplayMode(.inline)
                .padding(20)
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}
